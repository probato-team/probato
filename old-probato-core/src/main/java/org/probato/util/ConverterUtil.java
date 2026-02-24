package org.probato.util;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ConverterUtil {

	private static final Gson GSON;

	static {
		
		GSON = new GsonBuilder()
				.disableHtmlEscaping()
				.registerTypeAdapter(ZonedDateTime.class, new JsonSerializer<ZonedDateTime>() {

					@Override
					public JsonElement serialize(ZonedDateTime value, Type type, JsonSerializationContext context) {
						return new JsonPrimitive(value.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
					}
				})
				.create();
	}
	
	private ConverterUtil() {}
	
	public static String toJson(Object object) {
		return GSON.toJson(object);
	}
	
	public static <T> T toObject(String json, Class<T> clazz) {
		return GSON.fromJson(json, clazz);
	}

}