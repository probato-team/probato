package org.probato.engine;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.probato.loader.AnnotationLoader;

public class ProbatoClasspathScanner {

	private ProbatoClasspathScanner() {}

	public static Set<Class<?>> getSuites() {
		return getClasses()
				.stream()
				.filter(clazz -> clazz.isAnnotationPresent(org.probato.api.Suite.class))
				.sorted((suiteClazzA, suiteClazzB) -> {
					var suiteA = AnnotationLoader.getSuite(suiteClazzA).get();
					var suiteB = AnnotationLoader.getSuite(suiteClazzB).get();
					return suiteA.code().compareTo(suiteB.code());
				})
				.sorted((suiteClazzA, suiteClazzB) -> {
					var suiteA = AnnotationLoader.getSuite(suiteClazzA).get();
					var suiteB = AnnotationLoader.getSuite(suiteClazzB).get();
					return suiteA.name().compareTo(suiteB.name());
				})
				.collect(Collectors.toSet());
	}

	private static HashSet<Class<?>> getClasses() {
		var classpath = System.getProperty("java.class.path");
		var entries = classpath.split(File.pathSeparator);
		var loader = Thread.currentThread().getContextClassLoader();
		var classes = new HashSet<Class<?>>();
		for (var entry : entries) {
			var file = new File(entry);
			if (file.isDirectory()) {
				classes.addAll(scanDirectory(file, "", loader));
			}
		}
		return classes;
	}

	private static Set<Class<?>> scanDirectory(File dir, String pkg, ClassLoader loader) {

		var classes = new HashSet<Class<?>>();
		for (var file : dir.listFiles()) {
			if (file.isDirectory()) {

				classes.addAll(
					scanDirectory(
						file,
						pkg + file.getName() + ".",
						loader));

			} else if (file.getName().endsWith(".class")) {
				var className = pkg + file.getName().replace(".class", "");
				try {
					classes.add(loader.loadClass(className));
				} catch (Throwable ex) {
					// ignored
				}
			}
		}

		return classes;
	}

}