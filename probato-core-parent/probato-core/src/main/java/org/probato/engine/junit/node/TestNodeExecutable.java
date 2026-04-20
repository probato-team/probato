package org.probato.engine.junit.node;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.function.Executable;
import org.probato.engine.ExecutionContext;
import org.probato.engine.ExecutionEngine;
import org.probato.loader.AnnotationLoader;
import org.probato.model.Browser;

public class TestNodeExecutable implements Executable {

	private final ExecutionEngine engine;
	private final ExecutionContext context;

	public TestNodeExecutable(
			Browser browser,
			Class<?> suiteClazz,
			Class<?> scriptClazz,
			Integer datasetLine) {

		engine = ExecutionEngine.get();
		context = new ExecutionContext(browser, suiteClazz, scriptClazz, datasetLine);
	}

	@Override
	public void execute() throws Throwable {

		AnnotationLoader.getDisabled(context.getScriptClass())
			.ifPresent(disabled -> Assumptions.assumeFalse(Boolean.TRUE, disabled.value()));

		engine.execute(context);
	}

}