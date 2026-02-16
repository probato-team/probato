package org.probato.engine;

import org.junit.jupiter.api.DynamicTest;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;

public class ProbatoTestDescriptor extends AbstractTestDescriptor {

	private final DynamicTest dynamicTest;

	public ProbatoTestDescriptor(UniqueId id, DynamicTest test) {
		super(id, test.getDisplayName());
		this.dynamicTest = test;
	}

	@Override
	public Type getType() {
		return Type.TEST;
	}

	public void execute() throws Throwable {
		dynamicTest.getExecutable().execute();
	}

}