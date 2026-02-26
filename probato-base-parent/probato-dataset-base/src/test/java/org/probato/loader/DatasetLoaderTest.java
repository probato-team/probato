package org.probato.loader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.api.Dataset;
import org.probato.api.Disabled;
import org.probato.api.Ignore;
import org.probato.api.Procedure;
import org.probato.api.TestCase;

@DisplayName("UT - DatasetLoader")
class DatasetLoaderTest {

	@Test
	@DisplayName("Should verify dataset successfully")
	void shouldVefifyDatasetSuccessfully() {

		var hasDataset = DatasetLoader.hasDataset(TestDatasetLoader.class);

		assertTrue(hasDataset);
	}

	@Test
	@DisplayName("Should get dataset successfully")
	void shouldGetDatasetSuccessfully() throws Exception {

		var dataset = DatasetLoader.getDataset(TestDatasetLoader.class)
				.orElseThrow(() -> new Exception("Not found"));

		assertEquals("path/to/none.csv", dataset.value());
	}

	@Dataset("path/to/none.csv")
	@Ignore
	static class TestDatasetLoader {

		@Ignore
		String ignoredField;

		@Disabled("This a simple test")
		String disabledField;

		@TestCase
		String testCaseField;

		@Ignore
		@TestCase
		String testCaseIgnoredField;

		@Procedure
		String procedure;

		@Ignore
		@Procedure
		void procedure() {}

	}
}