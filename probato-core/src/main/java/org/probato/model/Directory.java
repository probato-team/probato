package org.probato.model;

public class Directory {

	private String temp = "/testano/temp";
	
	public Directory() {}

	public Directory(DirectoryBuilder builder) {
		this.temp = builder.temp;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public static DirectoryBuilder builder() {
		return new DirectoryBuilder();
	}
	
	public static class DirectoryBuilder {
		
		private String temp;
		
		private DirectoryBuilder() {}
		
		public DirectoryBuilder temp(String temp) {
			this.temp = temp;
			return this;
		}

		public Directory build() {
			return new Directory(this);
		}
	}

}