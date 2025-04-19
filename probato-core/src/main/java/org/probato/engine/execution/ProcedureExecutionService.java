package org.probato.engine.execution;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.probato.core.loader.AnnotationLoader;
import org.probato.engine.execution.proxy.PageProxy;
import org.probato.entity.model.PageObject;
import org.probato.service.DatasetService;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

public class ProcedureExecutionService {

	private InicializerExecutionService inicializerExecutionService;

	public ProcedureExecutionService() {
		this.inicializerExecutionService = new InicializerExecutionService();
	}

	public void execute(Object driver, Class<?> scriptClazz, Integer datasetLine, List<Object> procedures) throws Throwable {
		ExecutionContextHolder.cleanActions();
		ExecutionContextHolder.cleanSteps();
		for (Object procedure : procedures) {
			if (procedure instanceof Method) {
				invokeMethod(driver, scriptClazz, datasetLine, procedure);
			} else {
				invokeProcedure(driver, scriptClazz, datasetLine, procedure);
			}
		}
	}

	private void invokeMethod(Object driver, Class<?> scriptClazz, Integer datasetLine, Object item) throws Throwable {

		var method = (Method) item;
		var object = inicializerExecutionService.newInstance(method.getDeclaringClass());

		initializePagesObject(driver, method.getDeclaringClass(), object);
		executeMethod(scriptClazz, datasetLine, object, method);
	}

	private void invokeProcedure(Object driver, Class<?> scriptClazz, Integer datasetLine, Object item) throws Throwable {

		var field = (Field) item;
		var type = field.getType();
		var method = AnnotationLoader.getRunMethod(type);

		var objectScript = inicializerExecutionService.newInstance(field.getDeclaringClass());
		var objectProcedure = inicializerExecutionService.newInstance(type);

		field.setAccessible(Boolean.TRUE); // NOSONAR
		field.set(objectScript, objectProcedure); // NOSONAR

		initializePagesObject(driver, type, objectProcedure);
		executeMethod(scriptClazz, datasetLine, objectProcedure, method);
	}

	private void initializePagesObject(Object driver, Class<?> clazz, Object object) throws Exception {
		for (var page : AnnotationLoader.getPages(clazz)) {
			var pageObject = intancePageObjectProxy(page.getType());
			((PageObject) pageObject).setDriver(driver);
			page.setAccessible(Boolean.TRUE); // NOSONAR
			page.set(object, pageObject); // NOSONAR
		}
	}

	@SuppressWarnings({ "deprecation" })
	private <T> T intancePageObjectProxy(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		return new ByteBuddy()
				.subclass(clazz)
				.method(ElementMatchers.any())
				.intercept(InvocationHandlerAdapter.of(new PageProxy(clazz.newInstance())))
				.make()
				.load(clazz.getClassLoader())
				.getLoaded().newInstance();
	}

	private void executeMethod(Class<?> scriptClazz, Integer datasetLine, Object object, Method method) throws Throwable { // NOSONAR

		if (method.getGenericParameterTypes().length == 0) {

			method.setAccessible(Boolean.TRUE); // NOSONAR
			method.invoke(object);

		} else {

			var models = new ArrayList<>();
			for (Class<?> clazz : method.getParameterTypes()) {
				AnnotationLoader.getDataset(scriptClazz)
						.ifPresent(dataset -> models.add(DatasetService.getInstance().getDatamodel(dataset, clazz, datasetLine - 1)));
			}

			try {

				method.setAccessible(Boolean.TRUE); // NOSONAR
				method.invoke(object, models.toArray());

			} catch (Exception ex) {
				throw ex.getCause();
			}
		}
	}
}