package org.probato.model;

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

	public Content(ContentBuilder builder) {
		this();
		this.headers = builder.headers;
		this.data = builder.data;
	}

	public String[] getHeaders() {
		return headers;
	}

	public String[] getData() {
		return data;
	}

	public static ContentBuilder builder() {
		return new ContentBuilder();
	}

	public static class ContentBuilder {

		private String[] headers;
		private String[] data;

		private ContentBuilder() {}

		public ContentBuilder headers(String[] headers) {
			this.headers = headers;
			return this;
		}

		public ContentBuilder data(String[] data) {
			this.data = data;
			return this;
		}

		public Content build() {
			return new Content(this);
		}
	}

}