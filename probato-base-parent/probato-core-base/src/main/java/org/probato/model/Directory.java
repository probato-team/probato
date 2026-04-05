package org.probato.model;

import java.nio.file.Paths;

public class Directory {

	private String temp;
	private boolean keepClean;

	public Directory() {

		this.temp = Paths.get(System.getProperty("user.home"))
				.resolve(".probato")
				.toAbsolutePath()
				.normalize()
				.toString();

		this.keepClean = Boolean.FALSE;
	}

	public Directory(boolean keepClean) {
		this();
		this.keepClean = keepClean;
	}

	private Directory(DirectoryBuilder builder) {
		this();
		this.keepClean = builder.keepClean;
	}

	public String getTemp() {
		return temp;
	}

	public boolean isKeepClean() {
		return keepClean;
	}

	public void setKeepClean(boolean keepClean) {
		this.keepClean = keepClean;
	}

	public static DirectoryBuilder builder() {
		return new DirectoryBuilder();
	}

	public static class DirectoryBuilder {

		private boolean keepClean = Boolean.FALSE;

		private DirectoryBuilder() {}

		public DirectoryBuilder keepClean(boolean keepClean) {
			this.keepClean = keepClean;
			return this;
		}

		public Directory build() {
			return new Directory(this);
		}
	}

}