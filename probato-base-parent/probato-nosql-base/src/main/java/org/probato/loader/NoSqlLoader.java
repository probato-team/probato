package org.probato.loader;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.probato.api.NoSQL;
import org.probato.api.NoSQLs;

public class NoSqlLoader {

	private NoSqlLoader() {}

	public static boolean hasNoSql(Class<?> clazz) {
		return clazz.isAnnotationPresent(NoSQL.class)
				|| clazz.isAnnotationPresent(NoSQLs.class);
	}

	public static Map<String, List<String>> getNoSqlPaths(Class<?> clazz) {

		var map = new LinkedHashMap<String, List<String>>();
		if (Objects.nonNull(clazz.getSuperclass())) {
			map.putAll(getNoSqlPaths(clazz.getSuperclass()));
		}

		for (Annotation annotation : clazz.getAnnotations()) {
			if (annotation instanceof NoSQL) {
				var noSql = (NoSQL) annotation;
				getNoSqlPaths(map, noSql);
			} else if (annotation instanceof NoSQLs) {
				var sqLs = (NoSQLs) annotation;
				getNoSqlPaths(map, sqLs);
			}
		}

		return map;
	}

	private static void getNoSqlPaths(Map<String, List<String>> retorno, NoSQLs noSqLs) {
		Stream.of(noSqLs.value()).forEach(sql -> getNoSqlPaths(retorno, sql));
	}

	private static void getNoSqlPaths(Map<String, List<String>> retorno, NoSQL noSql) {
		if (retorno.containsKey(noSql.datasource())) {
			retorno.get(noSql.datasource()).addAll(Arrays.asList(noSql.scriptPath()));
		} else {
			retorno.put(noSql.datasource(), Arrays.asList(noSql.scriptPath()));
		}
	}

}