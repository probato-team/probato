package org.probato.engine.execution;

import java.util.ArrayList;
import java.util.List;

import org.probato.engine.execution.builder.Step;

public class ExecutionContextHolder {

	private static Long increment;
	private static String version;
	private static Integer indexAction = 0;
	private static Integer indexStep = 0;
	private static Object content;
	private static ThreadLocal<Boolean> collectionMode = new ThreadLocal<>();
	private static ThreadLocal<Throwable> throwable = new ThreadLocal<>();
	private static ThreadLocal<List<Step>> actions = new ThreadLocal<>();
	private static ThreadLocal<List<Step>> steps = new ThreadLocal<>();

	private ExecutionContextHolder() {}

	public static void setContent(Object content) {
		ExecutionContextHolder.content = content;
	}

	public static Object getContent() {
		return content;
	}

	public static void setIncrement(Long increment) {
		ExecutionContextHolder.increment = increment;
	}

	public static void setVersion(String version) {
		ExecutionContextHolder.version = version;
	}

	public static Long getIncrement() {
		return increment;
	}

	public static String getVersion() {
		return version;
	}

	public static boolean isCollectionMode() {
		return collectionMode.get();
	}

	public static void onCollectionMode() {
		ExecutionContextHolder.collectionMode.set(Boolean.TRUE);
	}

	public static void offCollectionMode() {
		ExecutionContextHolder.collectionMode.set(Boolean.FALSE);
	}

	public static void setThrowable(Throwable throwable) {
		ExecutionContextHolder.throwable.set(throwable);
	}

	public static Throwable getThrowable() {
		return ExecutionContextHolder.throwable.get();
	}

	public static void addAction(String action) {
		ExecutionContextHolder.actions.get().add(Step.builder()
				.sequence(indexAction++)
				.text(action)
				.build());
	}

	public static void addStep(String step) {
		ExecutionContextHolder.steps.get().add(Step.builder()
				.sequence(indexStep++)
				.text(step)
				.build());
	}

	public static List<Step> getActions() {
		return ExecutionContextHolder.actions.get();
	}

	public static List<Step> getSteps() {
		return ExecutionContextHolder.steps.get();
	}

	public static void cleanActions() {
		ExecutionContextHolder.indexAction = 0;
		ExecutionContextHolder.actions.remove();
		ExecutionContextHolder.actions.set(new ArrayList<>());
	}

	public static void cleanSteps() {
		ExecutionContextHolder.indexStep = 0;
		ExecutionContextHolder.steps.remove();
		ExecutionContextHolder.steps.set(new ArrayList<>());
	}

	public static void clean() {
		ExecutionContextHolder.content = null;
		ExecutionContextHolder.collectionMode.remove();
		ExecutionContextHolder.collectionMode.set(Boolean.FALSE);
		ExecutionContextHolder.throwable.remove();
		ExecutionContextHolder.cleanActions();
		ExecutionContextHolder.cleanSteps();
	}

}