package org.probato.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.probato.exception.IntegrityException;

import com.datastax.oss.driver.api.core.CqlSession;

public class CassandraUtils {

	private static final String EXT_CQL_FILE = ".cql";
	private static final String DATACENTER_TXT = "datacenter1";

	private CassandraUtils() {}

	public static boolean isValidFile(String pathname) {
		return Arrays.stream(getCqlFiles(pathname))
				.anyMatch(File::exists);
	}

	public static File[] getCqlFiles(String pathname) {

		File[] result = null;
		if (pathname.endsWith(EXT_CQL_FILE)) {
			var file = FileUtils.getFile(pathname);
			result = Objects.nonNull(file) ? new File[] { file } : new File[] {};
		}

		return result;
	}

	public static List<String> getCommands(String pathname) throws IOException {
		return getCommands(new File(FileUtils.getUrlResourceFile(pathname).getFile()));
	}

	public static List<String> getCommands(File file) throws IOException {

		var result = new ArrayList<String>();
		var content = new StringBuilder();
		try (var fr = new FileReader(file); var br = new BufferedReader(fr)) {

			String line;
			while ((line = br.readLine()) != null) {

				var builder = new StringBuilder(line.trim());

				removeComment("--", builder);
				removeComment("//", builder);
				removeComment("#", builder);

				if (StringUtils.isBlank(builder.toString())) {
					continue;
				}

				content.append(builder.toString()).append(" ");
			}
		}

		var commands = content.toString().split(";");
		for (var cmd : commands) {
			if (StringUtils.isNotBlank(cmd)) {
				result.add(cmd.trim());
			}
		}

		return result;
	}

	public static void validateConnection(String host, int port, String keyspace) {
		try (var session = CqlSession.builder()
				.addContactPoint(new InetSocketAddress(host, port))
				.withLocalDatacenter(DATACENTER_TXT)
				.withKeyspace(keyspace)
				.build()) {

			session.execute("SELECT release_version FROM system.local");
		}
	}

	public static void validateCommands(String host, int port, String keyspace, List<String> commands) {
		try (var session = CqlSession.builder()
				.addContactPoint(new InetSocketAddress(host, port))
				.withLocalDatacenter(DATACENTER_TXT)
				.withKeyspace(keyspace)
				.build()) {

			for (var command : commands) {
				if (StringUtils.isBlank(command)) {
					throw new IntegrityException("Invalid CQL command");
				}

				// TODO Implement syntax validation
			}
		}
	}

	public static void executeCommands(String host, int port, String keyspace, List<String> commands) {
		try (var session = CqlSession.builder()
				.addContactPoint(new InetSocketAddress(host, port))
				.withLocalDatacenter(DATACENTER_TXT)
				.withKeyspace(keyspace)
				.build()) {

			for (var command : commands) {
				if (StringUtils.isBlank(command)) {
					throw new IntegrityException("Invalid CQL command");
				}
				session.execute(command);
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