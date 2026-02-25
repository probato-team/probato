package org.probato.model;

import java.nio.file.Paths;

public class Directory {

	private String temp;

	public Directory() {
		this.temp = Paths.get(System.getProperty("user.home"))
	             .resolve(".probato")
	             .toAbsolutePath()
	             .normalize()
	             .toString();
	}

	public String getTemp() {
		return temp;
	}

}