package org.probato.engine.procedure;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.engine.ExecutionContext;
import org.probato.exception.IntegrityException;
import org.probato.test.script.UC01TC01_Script;
import org.probato.test.script.UC16TC01_ScriptPreconditionFailure;
import org.probato.test.script.UC17TC01_ScriptProcedureFailure;
import org.probato.test.script.UC18TC01_ScriptPostconditionFailure;
import org.probato.test.script.UC19TC01_ScriptPreconditionError;
import org.probato.test.script.UC20TC01_ScriptProcedureError;
import org.probato.test.script.UC21TC01_ScriptPostconditionError;
import org.probato.test.suite.UC01_Suite;
import org.probato.type.ExecutionStatus;

@DisplayName("UT -> ProcedureService")
class ProcedureServiceTest {

	@Test
	@DisplayName("Should load procedures by script class")
	void shouldLoadProceduresSuccessfully() {

		var service = ProcedureService.get();

		var executableUnits = service.load(UC01TC01_Script.class);

		assertEquals(6, executableUnits.size());
	}

	@Test
	@DisplayName("Should validate when not load procedures")
	void shouldValidateNotLoadProcedures() {

		var service = ProcedureService.get();

		var exception = assertThrows(IntegrityException.class, () -> service.load(null));

		assertTrue(exception.getMessage().startsWith("An error occurred while loading the procedures in script"));
	}

	@Test
	@DisplayName("Should execute procedures by script class")
	void shouldExecuteProceduresSuccessfully() { // NOSONAR

		var context = new ExecutionContext(null, UC01_Suite.class, UC01TC01_Script.class, 0);

		var service = ProcedureService.get();
		var executableUnits = service.load(UC01TC01_Script.class);

		var result = service.execute(context, UC01TC01_Script.class, executableUnits);

		assertAll("Validate data",
				() -> assertNotNull(result.getStatus()),
				() -> assertTrue(result.hasSuccess()),
				() -> assertNotNull(result.getCurrentPhase()),
				() -> assertNotNull(result.getRuntime()),
				() -> assertNull(result.getMotive()),
				() -> assertNull(result.getMotiveMessage()),
				() -> assertNotNull(result.getCollecedSteps()),
				() -> assertNotNull(result.getExecutedSteps()),
				() -> assertEquals(3, result.getCollecedSteps().size()),
				() -> assertEquals(3, result.getExecutedSteps().size()),
				() -> result.getCollecedSteps()
					.stream()
					.forEach(step -> {
						assertNotNull(step.getMethod());
						assertNotNull(step.getActionValue());
						assertNotNull(step.getStepValue());
						assertNotNull(step.hasSuccess());
						assertNotNull(step.getStart());
						assertNotNull(step.getEnd());
						assertNull(step.getError());
					}),
				() -> result.getExecutedSteps()
					.stream()
					.forEach(step -> {
						assertNotNull(step.getMethod());
						assertNotNull(step.getActionValue());
						assertNotNull(step.getStepValue());
						assertNotNull(step.hasSuccess());
						assertNotNull(step.getStart());
						assertNotNull(step.getEnd());
						assertNull(step.getError());
					}),
				() -> assertEquals(ExecutionStatus.PASSED, result.getStatus()));
	}

	@ParameterizedTest
	@MethodSource("getFailureErrorScript")
	@DisplayName("Should validate when error/failure procedures by script class")
	void shouldValidateFailureProcedure(Class<?> scriptClazz, ExecutionStatus expected) {

		var context = new ExecutionContext(null, UC01_Suite.class, scriptClazz, 0);

		var service = ProcedureService.get();
		var executableUnits = service.load(scriptClazz);

		var result = service.execute(context, scriptClazz, executableUnits);

		assertAll("Validate data",
				() -> assertEquals(expected, result.getStatus()));
	}

	private static Stream<Arguments> getFailureErrorScript() {
		return Stream.of(
				Arguments.of(
					UC16TC01_ScriptPreconditionFailure.class,
					ExecutionStatus.FAILED
				),
				Arguments.of(
					UC17TC01_ScriptProcedureFailure.class,
					ExecutionStatus.FAILED
				),
				Arguments.of(
					UC18TC01_ScriptPostconditionFailure.class,
					ExecutionStatus.FAILED
				),
				Arguments.of(
					UC19TC01_ScriptPreconditionError.class,
					ExecutionStatus.FAILED
				),
				Arguments.of(
					UC20TC01_ScriptProcedureError.class,
					ExecutionStatus.FAILED
				),
				Arguments.of(
					UC21TC01_ScriptPostconditionError.class,
					ExecutionStatus.FAILED
				));
	}

}