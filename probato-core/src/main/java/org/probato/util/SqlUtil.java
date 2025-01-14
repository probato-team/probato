package org.probato.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class SqlUtil {

	private static final String EXT_SQL_FILE = ".sql";

	private SqlUtil() {}

	public static boolean isValidFile(String pathname) {
		return Arrays.asList(getSqlFiles(pathname))
				.stream()
				.anyMatch(File::exists);
	}

	public static File[] getSqlFiles(String pathname) {

		File[] result = null;
		if (pathname.endsWith(EXT_SQL_FILE)) {
			File file = FileUtil.getFile(pathname);
			result = Objects.nonNull(file) ? new File[] { file } : new File[] {};
		}

		return result;
	}
	
	public static List<String> getQueries(String pathname) throws IOException {
		return getQueries(new File(FileUtil.getUrlResourceFile(pathname).getFile()));
	}

	public static List<String> getQueries(File file) throws IOException {

		List<String> result = new ArrayList<>();
		String queryLine = null;
		StringBuilder sqlContent = new StringBuilder();
		try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr);) {
			while ((queryLine = br.readLine()) != null) {

				StringBuilder builder = new StringBuilder(queryLine.trim());
				removeComment("#", builder);
				removeComment("--", builder);

				sqlContent.append(builder.toString().trim().concat(" "));
			}
		}

		String[] sqlQueries = sqlContent.toString().split(";");
		for (String sql : sqlQueries) {
			if (StringUtils.isBlank(sql)) {
				continue;
			}
			result.add(sql.trim());
		}

		return result;
	}
	
	public static void validateConnection(String url, String username, String password, String driver, String schema) throws SQLException, ClassNotFoundException {
		Class.forName(driver); 
		try (Connection conn = DriverManager.getConnection(url, username, password); Statement stmt = conn.createStatement()) {
			
			if (Objects.nonNull(schema)) {
				conn.setSchema(schema);
			}
			
			conn.setAutoCommit(Boolean.FALSE);
			stmt.executeQuery("SELECT 1");
		}
	}
	
	public static void validateQueries(String url, String username, String password, String driver, String schema, List<String> queries) throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		try (Connection conn = DriverManager.getConnection(url, username, password); Statement stmt = conn.createStatement()) {
			
			conn.setAutoCommit(Boolean.FALSE);
			if (Objects.nonNull(schema)) {
				conn.setSchema(schema);
			}
			
			for (String query : queries) {
				if (query.toUpperCase().startsWith("SELECT ")) {
					stmt.executeQuery(query);
				} else {
					stmt.executeUpdate(query);
				}
			}
		}
	}

	public static void executeQueries(String url, String username, String password, String driver, String schema, List<String> queries) throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		try (Connection conn = DriverManager.getConnection(url, username, password); Statement stmt = conn.createStatement()) {
			
			conn.setAutoCommit(Boolean.TRUE);
			if (Objects.nonNull(schema)) {
				conn.setSchema(schema);
			}
			
			for (String query : queries) {
				if (query.startsWith("SELECT ")) {
					stmt.executeQuery(query);
				} else {
					stmt.executeUpdate(query);
				}
			}
		}
	}

	private static void removeComment(String comment, StringBuilder builder) {
		int indexOfCommentSign = builder.indexOf(comment);
		if (indexOfCommentSign != -1) {
			if (builder.toString().startsWith(comment)) {
				builder.setLength(0);
			} else {
				builder.delete(indexOfCommentSign, builder.length());
			}
		}
	}

}