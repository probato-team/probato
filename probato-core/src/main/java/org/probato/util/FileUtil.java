package org.probato.util;

import static java.util.Optional.ofNullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtil {
	
	private static final String DOT_FILE = ".";
	
	private FileUtil() {}
	
	public static void save(String path, String name, String content) throws IOException {

		File dir = new File(path);
		if (!exists(dir)) {
			dir.mkdirs();
		}

		File file = new File(path, name);
		try (FileWriter fw = new FileWriter(file)) {
			fw.write(content);
		}
	}
	
	public static List<File> loadFiles(String path) {
		return loadFiles(new File(path));
	}
	
	public static List<File> loadFiles(File directory) {
		List<File> files = new ArrayList<>();
		if (directory.exists()) {
			for (File file : directory.listFiles()) {
				if (file.isDirectory()) {
					files.addAll(loadFiles(file));
				} else {
					files.add(file);
				}
			}
		}
		return files;
	}
	
	public static String getContent(File file) throws IOException {

		String result;
		FileReader reader = new FileReader(file);
		try (BufferedReader br = new BufferedReader(reader);) {

			String line;
			StringBuilder buffer = new StringBuilder();
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
			result = buffer.toString();
		}

		return result;
	}
	
	public static void createTempDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	public static void delete(File file) throws IOException {
		if (exists(file)) {
			Files.delete(file.toPath());
		}
	}
	
	public static boolean exists(File file) {
		return file.exists();
	}

	public static boolean exists(String pathname) {
		var fileResource = getUrlResourceFile(pathname);
		return Objects.nonNull(fileResource) && exists(new File(fileResource.getFile()));
	}
	
	public static File getFile(String pathname) {

		File result = null;
		if (existsInResources(pathname)) {
			URL url = getUrlResourceFile(pathname);
			result = new File(url.getFile());
		} else if (existsInPathname(pathname)) {
			result = new File(pathname);
		}

		return result;
	}
	
	public static File[] getFiles(String pathname, String format) {
		File file = getFile(pathname);
		return ofNullable(file)
				.map(item -> item.listFiles((dir, name) -> dir.isDirectory() && name.endsWith(DOT_FILE.concat(format))))
				.orElse(new File[] {});
	}

	public static boolean existsInResources(String path) {
		return ofNullable(getUrlResourceFile(path)).map(url -> existsInPathname(url.getFile())).orElse(Boolean.FALSE);
	}

	public static boolean existsInPathname(String path) {
		return new File(path).exists();
	}
	
	public static URL getUrlResourceFile(String pathname) {
		return FileUtil.class.getClassLoader().getResource(pathname);
	}
	
	public static String getFileExtension(String filename) {
		return com.google.common.io.Files.getFileExtension(filename);
	}

}