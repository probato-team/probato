package org.probato.loader;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.probato.api.SQL;
import org.probato.api.SQLs;

public class SqlLoader {

	private SqlLoader() {}

	public static boolean hasSql(Class<?> clazz) {
		return clazz.isAnnotationPresent(SQL.class)
				|| clazz.isAnnotationPresent(SQLs.class);
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