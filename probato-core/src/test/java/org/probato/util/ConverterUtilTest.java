package org.probato.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.test.datamodel.Data;

@DisplayName("Test -> ConverterUtil")
class ConverterUtilTest {

	@Test
	@DisplayName("Should converter to json successfully")
	void shouldConverterToJsonSuccessfully() {

		var value = ConverterUtil.toJson(new Data("name", ZonedDateTime.parse("2000-01-01T00:00:00+00:00")));

		assertEquals("{\"name\":\"name\",\"dateTime\":\"2000-01-01T00:00:00Z\"}", value);
	}

	@Test
	@DisplayName("Should converter to object successfully")
	void shouldConverterToObjectSuccessfully() {

		var data = "{\"name\":\"name\"}";

		var value = ConverterUtil.toObject(data, Data.class);

		assertEquals(new Data("name", null), value);
	}
}