package org.probato.integration.manager;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.probato.integration.manager.dto.RecordFileExecutionRequest;
import org.probato.model.type.ExecutionPhase;
import org.probato.util.ConverterUtil;
import org.probato.util.FileUtil;

public class SubmitExecutionDataApiService extends AbstractApiService {
	
	private static final String SUBMIT_SUITE_ENDPOINT = "{0}/core/suites";
	private static final String SUBMIT_SCRIPT_ENDPOINT = "{0}/core/scripts";
	private static final String SUBMIT_EXECUTION_ENDPOINT = "{0}/core/executions";
	private static final String SUBMIT_FILE_EXECUTION_ENDPOINT = "{0}/core/executions/file";
	private static final String DIRECTORY = "{0}/{1}/{2}";
	private static final String CONTENT_TYPE = "Content-Type";

	@Override
	public ExecutionPhase getExecutionPhase() {
		return ExecutionPhase.AFTER_EACH;
	}
	
	public void execute() throws Exception {

		var files = getExecutionFiles();
		for (var file : files) {

			var ext = FileUtil.getFileExtension(file.getName());
			switch (ext) {

				case "json":

					var name = file.getName();
					if (name.startsWith("000-")) {

						var url = buildUrl(SUBMIT_SUITE_ENDPOINT, getBaseUrl());
						submitPutContent(url, file);

					} else if (name.startsWith("001-")) {

						var url = buildUrl(SUBMIT_SCRIPT_ENDPOINT, getBaseUrl());
						submitPutContent(url, file);

					} else {

						var url = buildUrl(SUBMIT_EXECUTION_ENDPOINT, getBaseUrl());
						submitPostContent(url, file);
					}

					break;
				case "mp4":
				case "jpg":

					var url = buildUrl(SUBMIT_FILE_EXECUTION_ENDPOINT, getBaseUrl());
					submitFilePostContent(url, file);

					break;
				default:
					break;
			}
		}
	}

	private void submitPostContent(String url, File file) throws IOException, InterruptedException {

		var content = FileUtil.getContent(file);
		var request = HttpRequest.newBuilder()
			.POST(HttpRequest.BodyPublishers.ofString(content))
			.uri(URI.create(buildUrl(url)))
			.setHeader(X_PROJECT_TOKEN, getProjectToken())
			.setHeader(CONTENT_TYPE, "application/json")
			.build();

		send(request);
	}

	private void submitPutContent(String url, File file) throws IOException, InterruptedException {

		var content = FileUtil.getContent(file);
		var request = HttpRequest.newBuilder()
				.PUT(HttpRequest.BodyPublishers.ofString(content))
				.uri(URI.create(buildUrl(url)))
				.setHeader(X_PROJECT_TOKEN, getProjectToken())
				.setHeader(CONTENT_TYPE, "application/json")
				.build();

		send(request);
	}

	private void submitFilePostContent(String url, File file) throws IOException, InterruptedException {

		var command = RecordFileExecutionRequest.builder()
				.projectId(getProjectId())
				.projectVersion(getProjectVersion())
				.increment(getIncrement())
				.build();

		var data = new LinkedHashMap<Object, Object>();
		data.put("file", file.toPath());
		data.put("command", converterRequest(command));

		var boundary = new BigInteger(256, new Random()).toString();

		var request = HttpRequest.newBuilder()
			.POST(ofMimeMultipartData(data, boundary))
			.uri(URI.create(buildUrl(url)))
			.setHeader(X_PROJECT_TOKEN, getProjectToken())
			.setHeader(CONTENT_TYPE, "multipart/form-data; boundary=" + boundary)
			.build();

		send(request);
	}
	
	private List<File> getExecutionFiles() {
		var path = buildUrl(DIRECTORY, getTempDir(), getProjectId(), getProjectVersion());
		return FileUtil.loadFiles(path);
	}
	
	private String converterRequest(Object object) {
		return ConverterUtil.toJson(object);
	}
	
	private static HttpRequest.BodyPublisher ofMimeMultipartData(Map<Object, Object> data, String boundary) throws IOException {
		
		var byteArrays = new ArrayList<byte[]>();
		var separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=").getBytes(StandardCharsets.UTF_8);
		for (var entry : data.entrySet()) {

			byteArrays.add(separator);

			if (entry.getValue() instanceof Path) {

				var path = (Path) entry.getValue();
				var mimeType = java.nio.file.Files.probeContentType(path);
				byteArrays.add(("\"" + entry.getKey() + "\"; filename=\"" + path.getFileName() + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
				byteArrays.add(java.nio.file.Files.readAllBytes(path));
				byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
				
			} else {
				
				byteArrays.add(("\"" + entry.getKey() + "\"; filename=\"" + entry.getKey() + "\"\r\nContent-Type: " + "application/json" + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
				byteArrays.add(entry.getValue().toString().getBytes());
				byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
			}
		}
		byteArrays.add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));

		return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
	}

}