package org.probato.model;

import org.probato.type.Quality;

public class Video {

	private double frameRate = 10;
	private int bitrate = 6_000_000;
	private long startDelay = 500;
	private long stopDelay = 1_000;
	private boolean enabled = Boolean.FALSE;
	private Quality quality = Quality.MEDIUM;

	public Video() {}

	public Video(
			double frameRate,
			int bitrate,
			long startDelay,
			long stopDelay,
			Boolean enabled,
			Quality quality) {

		this();
		this.frameRate = frameRate;
		this.bitrate = bitrate;
		this.startDelay = startDelay;
		this.stopDelay = stopDelay;
		this.enabled = enabled;
		this.quality = quality;
	}

	public Video(VideoBuilder builder) {
		this();
		this.frameRate = builder.frameRate;
		this.bitrate = builder.bitrate;
		this.startDelay = builder.startDelay;
		this.stopDelay = builder.stopDelay;
		this.enabled = builder.enabled;
		this.quality = builder.quality;
	}

	public double getFrameRate() {
		return frameRate;
	}

	public void setFrameRate(double frameRate) {
		this.frameRate = frameRate;
	}

	public int getBitrate() {
		return bitrate;
	}

	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

	public long getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(long startDelay) {
		this.startDelay = startDelay;
	}

	public long getStopDelay() {
		return stopDelay;
	}

	public void setStopDelay(long stopDelay) {
		this.stopDelay = stopDelay;
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
		private int bitrate = 6_000_000;
		private long startDelay = 6_000_000;
		private long stopDelay = 6_000_000;
		private boolean enabled = Boolean.FALSE;
		private Quality quality = Quality.MEDIUM;

		private VideoBuilder() {}

		public VideoBuilder frameRate(double frameRate) {
			this.frameRate = frameRate;
			return this;
		}

		public VideoBuilder bitrate(int bitrate) {
			this.bitrate = bitrate;
			return this;
		}

		public VideoBuilder startDelay(long startDelay) {
			this.startDelay = startDelay;
			return this;
		}

		public VideoBuilder stopDelay(long stopDelay) {
			this.stopDelay = stopDelay;
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