package org.probato.entity.model;

public class Content {

	private String[] headers;
	private String[] data;

	public Content() {
		this.headers = new String[] {};
		this.data = new String[] {};
	}

	public Content(String[] headers, String[] data) {
		this.headers = headers;
		this.data = data;
	}

	public String[] getHeaders() {
		return headers;
	}

	public String[] getData() {
		return data;
	}

}