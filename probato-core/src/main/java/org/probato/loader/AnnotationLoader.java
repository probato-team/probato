package org.probato.loader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.probato.api.Action;
import org.probato.api.Dataset;
import org.probato.api.Disabled;
import org.probato.api.Ignore;
import org.probato.api.Page;
import org.probato.api.Postcondition;
import org.probato.api.Precondition;
import org.probato.api.Procedure;
import org.probato.api.Run;
import org.probato.api.SQL;
import org.probato.api.SQLs;
import org.probato.api.Script;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.model.PageObject;

public class AnnotationLoader {

	private AnnotationLoader() {}

	public static boolean isIgnore(Class<?> clazz) {
		return clazz.isAnnotationPresent(Ignore.class);
	}

	public static boolean isIgnore(Method method) {
		return Objects.nonNull(method) && method.isAnnotationPresent(Ignore.class);
	}

	public static boolean isIgnore(Field field) {
		return field.isAnnotationPresent(Ignore.class);
	}

	public static boolean isDisabled(Field field) {
		return field.isAnnotationPresent(Disabled.class);
	}

	public static Optional<Disabled> getDisabled(Field field) {
		return Optional.ofNullable(field.getAnnotation(Disabled.class));
	}

	public static String getDisabledMotive(Field field) {
		return getDisabled(field).map(Disabled::value).orElse("");
	}

	public static boolean isTestCase(Field field) {
		return field.isAnnotationPresent(TestCase.class);
	}

	public static boolean hasSql(Class<?> clazz) {
		return clazz.isAnnotationPresent(SQL.class)
				|| clazz.isAnnotationPresent(SQLs.class);
	}

	public static Stream<Class<?>> getTestsCase(Class<?> clazz) {
		return Stream.of(clazz.getDeclaredFields())
				.filter(AnnotationLoader::isTestCase)
				.map(Field::getType);
	}

	public static List<Field> getTestCaseField(Class<?> clazz) {
		return Stream.of(clazz.getDeclaredFields())
				.filter(AnnotationLoader::isTestCase)
				.collect(Collectors.toList());
	}

	public static Optional<Suite> getSuite(Class<?> clazz) {
		return Optional.ofNullable(clazz.getAnnotation(Suite.class));
	}

	public static Optional<Script> getScript(Class<?> clazz) {
		return Optional.ofNullable(clazz.getAnnotation(Script.class));
	}

	public static List<Class<?>> getPreconditions(Class<?> clazz) {
		return Stream.of(clazz.getDeclaredFields())
				.filter(AnnotationLoader::isPrecondition)
				.map(Field::getType)
				.collect(Collectors.toList());
	}

	public static List<Method> getPreconditionsMethod(Class<?> clazz) {
		return Stream.of(clazz.getDeclaredMethods())
				.filter(AnnotationLoader::isPrecondition)
				.collect(Collectors.toList());
	}

	public static List<Class<?>> getProcedures(Class<?> clazz) {
		return Stream.of(clazz.getDeclaredFields())
				.filter(AnnotationLoader::isProcedure)
				.map(Field::getType)
				.collect(Collectors.toList());
	}

	public static List<Method> getProceduresMethod(Class<?> clazz) {
		return Stream.of(clazz.getDeclaredMethods())
				.filter(AnnotationLoader::isProcedure)
				.collect(Collectors.toList());
	}

	public static List<Class<?>> getPostconditions(Class<?> clazz) {
		return Stream.of(clazz.getDeclaredFields())
				.filter(AnnotationLoader::isPostcondition)
				.map(Field::getType)
				.collect(Collectors.toList());
	}

	public static  List<Method> getPostconditionsMethod(Class<?> clazz) {
		return Stream.of(clazz.getDeclaredMethods())
				.filter(AnnotationLoader::isPostcondition)
				.collect(Collectors.toList());
	}

	public static boolean isPrecondition(Field field) {
		return field.isAnnotationPresent(Precondition.class);
	}

	public static boolean isPrecondition(Method method) {
		return method.isAnnotationPresent(Precondition.class);
	}

	public static boolean isProcedure(Field field) {
		return field.isAnnotationPresent(Procedure.class);
	}

	public static boolean isProcedure(Method method) {
		return method.isAnnotationPresent(Procedure.class);
	}

	public static boolean isPostcondition(Field field) {
		return field.isAnnotationPresent(Postcondition.class);
	}

	public static boolean isPostcondition(Method method) {
		return method.isAnnotationPresent(Postcondition.class);
	}

	public static boolean hasDataset(Class<?> clazz) {
		return clazz.isAnnotationPresent(Dataset.class);
	}

	public static Optional<Dataset> getDataset(Class<?> clazz) {
		return Optional.ofNullable(clazz.getAnnotation(Dataset.class));
	}

	public static Method getRunMethod(Class<?> procedureClazz) {
		return Stream.of(procedureClazz.getDeclaredMethods())
			.filter(method -> method.isAnnotationPresent(Run.class))
			.findFirst()
			.orElse(null);
	}

	public static List<Method> getActionMethods(Class<?> pageObjectClazz) {
		
		var methods = new ArrayList<Method>();
		if (Objects.nonNull(pageObjectClazz) && PageObject.class.isAssignableFrom(pageObjectClazz)) {
			methods.addAll(getActionMethods(pageObjectClazz.getSuperclass()));
			methods.addAll(Arrays.asList(pageObjectClazz.getDeclaredMethods()).stream()
					.filter(method -> method.isAnnotationPresent(Action.class))
					.collect(Collectors.toList()));
		}

		return methods;
	}

	public static String getActionValue(Method method) {
		return method.getAnnotation(Action.class).value();
	}

	public static List<Field> getPagesObject(Class<?> clazz) {

		var list = new ArrayList<Field>();
		Stream.of(clazz.getDeclaredFields())
				.forEach(field -> {
					if (field.isAnnotationPresent(Page.class)) {
						list.add(field);
					}
				});

		Optional.ofNullable(clazz.getSuperclass())
				.ifPresent(superClazz -> list.addAll(getPagesObject(superClazz)));

		return list;
	}

	public static List<Object> getProceduresScript(Class<?> scriptClazz, Class<? extends Annotation> procedureClazz) {

		var list = new ArrayList<Object>();
		list.addAll(getProcedureMethods(scriptClazz, procedureClazz));
		list.addAll(getProcedureFields(scriptClazz, procedureClazz));

		return list;
	}
	
	public static List<Field> getPages(Class<?> scriptClazz) {

		var list = new ArrayList<Field>();
		Stream.of(scriptClazz.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(Page.class))
				.forEach(list::add);

		Optional.ofNullable(scriptClazz.getSuperclass())
				.ifPresent(superClazz -> list.addAll(getPages(superClazz)));

		return list;
	}
	
	public static Map<String, List<String>> getSqlPaths(Class<?> clazz) {

		var map = new LinkedHashMap<String, List<String>>();
		if (Objects.nonNull(clazz.getSuperclass())) {
			map.putAll(getSqlPaths(clazz.getSuperclass()));
		}

		for (Annotation annotation : clazz.getAnnotations()) {
			if (annotation instanceof SQL) {
				var sql = (SQL) annotation;
				getSqlPaths(map, sql);
			} else if (annotation instanceof SQLs) {
				var sqLs = (SQLs) annotation;
				getSqlPaths(map, sqLs);
			}
		}

		return map;
	}

	private static List<Method> getProcedureMethods(Class<?> scriptClazz, Class<? extends Annotation> procedureClazz) {
		return Stream.of(scriptClazz.getDeclaredMethods())
				.filter(method -> hasExecutionCondition(method, procedureClazz))
				.collect(Collectors.toList());
	}

	private static List<Field> getProcedureFields(Class<?> scriptClass, Class<? extends Annotation> procedureClazz) {
		return Stream.of(scriptClass.getDeclaredFields())
				.filter(method -> hasExecutionCondition(method, procedureClazz))
				.collect(Collectors.toList());
	}

	private static boolean hasExecutionCondition(Method method, Class<? extends Annotation> procedureClazz) {
		return method.isAnnotationPresent(procedureClazz) 
				&& !(method.isAnnotationPresent(Ignore.class) || method.getDeclaringClass().isAnnotationPresent(Ignore.class));
	}

	private static boolean hasExecutionCondition(Field field, Class<? extends Annotation> procedureClazz) {
		return field.isAnnotationPresent(procedureClazz)
				&& !(field.isAnnotationPresent(Ignore.class) || field.getType().isAnnotationPresent(Ignore.class));
	}

	private static void getSqlPaths(Map<String, List<String>> retorno, SQLs sqLs) {
		Stream.of(sqLs.value()).forEach(sql -> getSqlPaths(retorno, sql));
	}

	private static void getSqlPaths(Map<String, List<String>> retorno, SQL sql) {
		if (retorno.containsKey(sql.datasource())) {
			retorno.get(sql.datasource()).addAll(Arrays.asList(sql.scriptPath()));
		} else {
			retorno.put(sql.datasource(), Arrays.asList(sql.scriptPath()));
		}
	}
}