package org.probato.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.type.Quality;

@DisplayName("UT - Video")
class VideoTest {

	@Test
	@DisplayName("Should create constructor object successfully")
	void shouldCreateConstructorObjectSuccessfully() {

		var frameRate = 20;
		var bitrate = 20_000_000;
		var startDelay = 200L;
		var stopDelay = 2_000L;
		var enabled = Boolean.TRUE;
		var quality = Quality.HIGH;

		var model = new Video(
				frameRate,
				bitrate,
				startDelay,
				stopDelay,
				enabled,
				quality);

		assertAll("Validate value",
				() -> assertEquals(frameRate, model.getFrameRate()),
				() -> assertEquals(bitrate, model.getBitrate()),
				() -> assertEquals(startDelay, model.getStartDelay()),
				() -> assertEquals(stopDelay, model.getStopDelay()),
				() -> assertEquals(enabled, model.isEnabled()),
				() -> assertEquals(quality, model.getQuality()));
	}

	@Test
	@DisplayName("Should create default constructor object successfully")
	void shouldCreateDefauldConstructorObjectSuccessfully() {

		var frameRate = 20;
		var bitrate = 20_000_000;
		var startDelay = 200L;
		var stopDelay = 2_000L;
		var enabled = Boolean.TRUE;
		var quality = Quality.HIGH;

		var model = new Video();
		model.setFrameRate(frameRate);
		model.setBitrate(bitrate);
		model.setStartDelay(startDelay);
		model.setStopDelay(stopDelay);
		model.setEnabled(enabled);
		model.setQuality(quality);

		assertAll("Validate value",
				() -> assertEquals(frameRate, model.getFrameRate()),
				() -> assertEquals(bitrate, model.getBitrate()),
				() -> assertEquals(startDelay, model.getStartDelay()),
				() -> assertEquals(stopDelay, model.getStopDelay()),
				() -> assertEquals(enabled, model.isEnabled()),
				() -> assertEquals(quality, model.getQuality()));
	}

	@Test
	@DisplayName("Should create build object successfully")
	void shouldCreateBuildObjectSuccessfully() {

		var frameRate = 20;
		var bitrate = 20_000_000;
		var startDelay = 200L;
		var stopDelay = 2_000L;
		var enabled = Boolean.TRUE;
		var quality = Quality.HIGH;

		var model = Video.builder()
				.frameRate(frameRate)
				.bitrate(bitrate)
				.startDelay(startDelay)
				.stopDelay(stopDelay)
				.enabled(enabled)
				.quality(quality)
				.build();

		assertAll("Validate value",
				() -> assertEquals(frameRate, model.getFrameRate()),
				() -> assertEquals(bitrate, model.getBitrate()),
				() -> assertEquals(startDelay, model.getStartDelay()),
				() -> assertEquals(stopDelay, model.getStopDelay()),
				() -> assertEquals(enabled, model.isEnabled()),
				() -> assertEquals(quality, model.getQuality()));
	}

}