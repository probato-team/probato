package org.probato.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.probato.exception.IntegrityException;

import com.mongodb.client.MongoClients;

public class MongoDbUtils {

	private static final String EXT_JSON_FILE = ".json";
	private static final String COLLECTION_TXT = "collection";

	private MongoDbUtils() {}

	public static boolean isValidFile(String pathname) {
		return Arrays.stream(getNoSqlFiles(pathname))
				.anyMatch(File::exists);
	}

	public static File[] getNoSqlFiles(String pathname) {

		File[] result = null;
		if (pathname.endsWith(EXT_JSON_FILE)) {
			var file = FileUtils.getFile(pathname);
			result = Objects.nonNull(file) ? new File[] { file } : new File[] {};
		}

		return result;
	}

	public static List<Document> getDocuments(String pathname) throws IOException {
		return getDocuments(new File(FileUtils.getUrlResourceFile(pathname).getFile()));
	}

	public static List<Document> getDocuments(File file) throws IOException {

		var result = new ArrayList<Document>();
		try (var fr = new FileReader(file); var br = new BufferedReader(fr);) {

			String line;

			while ((line = br.readLine()) != null) {

				var builder = new StringBuilder(line.trim());

				removeComment("#", builder);
				removeComment("//", builder);

				if (StringUtils.isBlank(builder.toString())) {
					continue;
				}

				result.add(Document.parse(builder.toString()));
			}
		}

		return result;
	}

	public static void validateConnection(String uri, String database, String username, String password) {

		var parsed = URI.create(uri);
		var url = buildMongoUri(
				parsed.getHost(),
				parsed.getPort(),
				database,
				username,
				password,
				database);

		try (var client = MongoClients.create(url)) {
			client.getDatabase(database);
		}
	}

	public static void validateDocuments(String uri, String database, String username, String password, List<Document> documents) {

		var parsed = URI.create(uri);
		var url = buildMongoUri(
				parsed.getHost(),
				parsed.getPort(),
				database,
				username,
				password,
				database);

		try (var client = MongoClients.create(url)) {
			var db = client.getDatabase(database);
			for (var document : documents) {

				if (!document.containsKey(COLLECTION_TXT) || !document.containsKey("operation")) {
					throw new IntegrityException("Invalid document: {0}", document.toJson());
				}

				db.getCollection(document.getString(COLLECTION_TXT));
			}
		}
	}

	public static void executeDocuments(String uri, String database, String username, String password, List<Document> documents) {

		var parsed = URI.create(uri);
		var url = buildMongoUri(
				parsed.getHost(),
				parsed.getPort(),
				database,
				username,
				password,
				database);

		try (var client = MongoClients.create(url)) {

			var db = client.getDatabase(database);
			for (var document : documents) {

				var collection = document.getString(COLLECTION_TXT);
				var operation = document.getString("operation");
				var mongoCollection = db.getCollection(collection);

				switch (operation) {

					case "insertOne":
						mongoCollection.insertOne((Document) document.get("document"));
						break;

					case "deleteMany":
						mongoCollection.deleteMany((Document) document.get("filter"));
						break;

					case "updateMany":
						mongoCollection.updateMany((Document) document.get("filter"), (Document) document.get("update"));
						break;

					default:
						throw new IntegrityException("Operation not supported: {0}", operation);
				}
			}
		}
	}

	public static String buildMongoUri(
			String host,
			int port,
			String database,
			String username,
			String password,
			String authSource
	) {


			if (authSource == null || authSource.isBlank()) {
				authSource = database;
			}

			return String.format(
					"mongodb://%s:%s@%s:%d/%s?authSource=%s",
					username,
					password,
					host,
					port,
					database,
					authSource
			);
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