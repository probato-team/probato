package org.probato.model;

import org.probato.model.type.Quality;

public class Video {

	private double frameRate = 10;
	private boolean enabled = Boolean.FALSE;
	private Quality quality = Quality.MEDIUM;

	public Video() {}

	public Video(VideoBuilder builder) {
		this.frameRate = builder.frameRate;
		this.enabled = builder.enabled;
		this.quality = builder.quality;
	}

	public double getFrameRate() {
		return frameRate;
	}

	public void setFrameRate(double frameRate) {
		this.frameRate = frameRate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Quality getQuality() {
		return quality;
	}

	public void setQuality(Quality quality) {
		this.quality = quality;
	}

	public static VideoBuilder builder() {
		return new VideoBuilder();
	}
	
	public static class VideoBuilder {

		private double frameRate = 10;
		private boolean enabled = Boolean.FALSE;
		private Quality quality = Quality.MEDIUM;

		private VideoBuilder() {}

		public VideoBuilder frameRate(double frameRate) {
			this.frameRate = frameRate;
			return this;
		}

		public VideoBuilder enabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public VideoBuilder quality(Quality quality) {
			this.quality = quality;
			return this;
		}

		public Video build() {
			return new Video(this);
		}
	}

}