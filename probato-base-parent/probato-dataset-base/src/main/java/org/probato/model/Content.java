package org.probato.model;

import java.util.Map;

public class Content {

	private String[] headers;
	private Object data;

	public Content() {
		this.headers = new String[] {};
		this.data = new String[] {};
	}

	public Content(String[] headers, String[] data) {
		this.headers = headers;
		this.data = data;
	}

	public Content(ContentBuilder builder) {
		this.headers = builder.headers;
		this.data = builder.data;
	}

	public Content(Map<String, Object> map) {
		this.headers = new String[] {};
		this.data = map;
	}

	public String[] getHeaders() {
		return headers;
	}

	public Object getData() {
		return data;
	}

	public static ContentBuilder builder() {
		return new ContentBuilder();
	}

	public static class ContentBuilder {

		private String[] headers;
		private Object data;

		private ContentBuilder() {}

		public ContentBuilder headers(String[] headers) {
			this.headers = headers;
			return this;
		}

		public ContentBuilder data(Object data) {
			this.data = data;
			return this;
		}

		public Content build() {
			return new Content(this);
		}
	}

}