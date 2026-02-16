package org.probato.engine;

import java.text.MessageFormat;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;

public class ProbatoDatasetDescriptor extends AbstractTestDescriptor {

	private static final String DISPLAY_NAME = "Dataset {0}";

	public ProbatoDatasetDescriptor(UniqueId id, Integer datasetLine) {
		super(id, buildDisplayName(datasetLine));
	}

	@Override
	public Type getType() {
		return Type.CONTAINER;
	}

	public static String buildDisplayName(Integer datasetLine) {
		return MessageFormat.format(DISPLAY_NAME, datasetLine);
	}

}