package org.probato.engine.procedure;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.probato.loader.AnnotationLoader;
import org.probato.loader.DatasetLoader;
import org.probato.model.PageObject;
import org.probato.service.DatasetService;
import org.probato.type.PhaseType;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

public interface ExecutableUnit {

	public PhaseType getPhase();

	public int getOrder();

	public void execute(Object driver, Integer datasetLine) throws Exception;

	default void initializePagesObject(Object driver, Class<?> clazz, Object object) throws Exception {
		for (var page : AnnotationLoader.getPages(clazz)) {
			var pageObject = intancePageObjectProxy(page.getType());
			((PageObject) pageObject).setDriver(driver);
			page.setAccessible(Boolean.TRUE); // NOSONAR
			page.set(object, pageObject); // NOSONAR
		}
	}

	@SuppressWarnings({ "deprecation" })
	default <T> T intancePageObjectProxy(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		return new ByteBuddy()
				.subclass(clazz)
				.method(ElementMatchers.any())
				.intercept(InvocationHandlerAdapter.of(new PageProxy(clazz.newInstance())))
				.make()
				.load(clazz.getClassLoader())
				.getLoaded().newInstance();
	}

	default void executeMethod(Class<?> scriptClazz, Integer datasetLine, Object object, Method method) throws Exception { // NOSONAR

		if (method.getGenericParameterTypes().length == 0) {

			method.setAccessible(Boolean.TRUE); // NOSONAR
			method.invoke(object);

		} else {

			var models = new ArrayList<>();
			for (Class<?> clazz : method.getParameterTypes()) {
				DatasetLoader.getDataset(scriptClazz)
						.ifPresent(dataset -> models.add(DatasetService.get().getDatamodel(dataset, clazz, datasetLine - 1)));
			}

			method.setAccessible(Boolean.TRUE); // NOSONAR
			method.invoke(object, models.toArray());
		}
	}

}