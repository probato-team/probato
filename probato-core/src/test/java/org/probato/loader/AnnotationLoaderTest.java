package org.probato.loader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.api.Dataset;
import org.probato.api.Disabled;
import org.probato.api.Ignore;
import org.probato.api.Procedure;
import org.probato.api.TestCase;
import org.probato.test.page.PrincipalPage;
import org.probato.test.procedure.ProcedureRun;
import org.probato.test.script.UC01TC01_Script;
import org.probato.test.script.UC10TC02_ScriptWithSql;
import org.probato.test.suite.UC01_Suite;
import org.probato.test.suite.UC10_SuiteWithSql;

@DisplayName("Test - AnnotationLoader")
class AnnotationLoaderTest {

	@Test
	@DisplayName("Should verify ignore class successfully")
	void shouldVefifyIgnoreClassSuccessfully() {

		var ignored = AnnotationLoader.isIgnore(TestAnnotationLoader.class);

		assertTrue(ignored);
	}

	@Test
	@DisplayName("Should verify ignore method successfully")
	void shouldVefifyIgnoreMethodSuccessfully() {

		var method = Stream.of(TestAnnotationLoader.class.getDeclaredMethods())
				.filter(item -> item.isAnnotationPresent(Procedure.class))
				.findFirst()
				.orElse(null);

		boolean ignored = AnnotationLoader.isIgnore(method);

		assertTrue(ignored);
	}

	@Test
	@DisplayName("Should verify ignore method null successfully")
	void shouldVefifyIgnoreMethodNullSuccessfully() {

		Method method = null;

		var ignored = AnnotationLoader.isIgnore(method);

		assertFalse(ignored);
	}

	@Test
	@DisplayName("Should verify ignore field successfully")
	void shouldVefifyIgnoreFieldSuccessfully() throws Exception {

		var ignored = AnnotationLoader.isIgnore(TestAnnotationLoader.class.getDeclaredField("ignoredField"));

		assertTrue(ignored);
	}

	@Test
	@DisplayName("Should verify disable field successfully")
	void shouldVefifyDisableFieldSuccessfully() throws Exception {

		var isDisabled = AnnotationLoader.isDisabled(TestAnnotationLoader.class.getDeclaredField("disabledField"));

		assertTrue(isDisabled);
	}

	@Test
	@DisplayName("Should get disable successfully")
	void shouldGetDisableSuccessfully() throws Exception {

		var disabled = AnnotationLoader
				.getDisabled(TestAnnotationLoader.class.getDeclaredField("disabledField"))
				.orElseThrow(() -> new Exception("Not found"));

		assertEquals("This a simple test", disabled.value());
	}

	@Test
	@DisplayName("Should get disable description successfully")
	void shouldGetDisableDescriptionSuccessfully() throws Exception {

		var disabledDescription = AnnotationLoader.getDisabledMotive(TestAnnotationLoader.class.getDeclaredField("disabledField"));

		assertEquals("This a simple test", disabledDescription);
	}
	
	@Test
	@DisplayName("Should verify test case field successfully")
	void shouldVefifyTestCaseFieldSuccessfully() throws Exception {

		var isTestCase = AnnotationLoader.isTestCase(TestAnnotationLoader.class.getDeclaredField("testCaseField"));

		assertTrue(isTestCase);
	}

	@Test
	@DisplayName("Should get test case class successfully")
	void shouldGetTestCaseClassSuccessfully() {

		var testsCase = AnnotationLoader.getTestsCase(TestAnnotationLoader.class);

		assertEquals(2, testsCase.collect(Collectors.toList()).size());
	}

	@Test
	@DisplayName("Should get test case field successfully")
	void shouldGetTestCaseFieldSuccessfully() {

		var testsCase = AnnotationLoader.getTestCaseField(TestAnnotationLoader.class);

		assertEquals(2, testsCase.size());
	}

	@Test
	@DisplayName("Should get suite successfully")
	void shouldGetSuiteSuccessfully() throws Exception {

		var suite = AnnotationLoader.getSuite(UC01_Suite.class).orElseThrow(() -> new Exception("Not found"));

		assertEquals("UC01", suite.code());
	}

	@Test
	@DisplayName("Should get script successfully")
	void shouldGetScriptSuccessfully() throws Exception {

		var script = AnnotationLoader.getScript(UC01TC01_Script.class).orElseThrow(() -> new Exception("Not found"));

		assertEquals("UC01TC01", script.code());
	}

	@Test
	@DisplayName("Should get preconditions successfully")
	void shouldGetPreconditionsSuccessfully() {

		var preconditions = AnnotationLoader.getPreconditions(TestAnnotationLoader.class);

		assertNotNull(preconditions);
		assertEquals(0, preconditions.size());
	}

	@Test
	@DisplayName("Should get preconditions methods successfully")
	void shouldGetPreconditionsMethodsSuccessfully() {

		var preconditions = AnnotationLoader.getPreconditionsMethod(TestAnnotationLoader.class);

		assertNotNull(preconditions);
		assertEquals(0, preconditions.size());
	}

	@Test
	@DisplayName("Should get procedures successfully")
	void shouldGetProceduresSuccessfully() {

		var procedures = AnnotationLoader.getProcedures(TestAnnotationLoader.class);

		assertNotNull(procedures);
		assertEquals(1, procedures.size());
	}

	@Test
	@DisplayName("Should get procedures methods successfully")
	void shouldGetProceduresMethosSuccessfully() {

		var procedures = AnnotationLoader.getProceduresMethod(TestAnnotationLoader.class);

		assertNotNull(procedures);
		assertEquals(1, procedures.size());
	}

	@Test
	@DisplayName("Should get posconditions successfully")
	void shouldGetPosconditionsSuccessfully() {

		var posconditions = AnnotationLoader.getPostconditions(TestAnnotationLoader.class);

		assertNotNull(posconditions);
		assertEquals(0, posconditions.size());
	}

	@Test
	@DisplayName("Should get posconditions methods successfully")
	void shouldGetPosconditionsMethodsSuccessfully() {

		var posconditions = AnnotationLoader.getPostconditionsMethod(TestAnnotationLoader.class);

		assertNotNull(posconditions);
		assertEquals(0, posconditions.size());
	}

	@Test
	@DisplayName("Should verify procedure field successfully")
	void shouldVefifyProcedureFieldSuccessfully() throws Exception {

		var isProcedure = AnnotationLoader.isProcedure(TestAnnotationLoader.class.getDeclaredField("procedure"));

		assertTrue(isProcedure);
	}

	@Test
	@DisplayName("Should verify procedure method successfully")
	void shouldVefifyProcedureMethodSuccessfully() throws Exception {

		var isProcedure = AnnotationLoader.isProcedure(TestAnnotationLoader.class.getDeclaredMethod("procedure"));

		assertTrue(isProcedure);
	}

	@Test
	@DisplayName("Should verify dataset successfully")
	void shouldVefifyDatasetSuccessfully() {

		var hasDataset = AnnotationLoader.hasDataset(TestAnnotationLoader.class);

		assertTrue(hasDataset);
	}

	@Test
	@DisplayName("Should get dataset successfully")
	void shouldGetDatasetSuccessfully() throws Exception {

		var dataset = AnnotationLoader.getDataset(TestAnnotationLoader.class)
				.orElseThrow(() -> new Exception("Not found"));

		assertEquals("path/to/none.csv", dataset.value()[0]);
	}

	@Test
	@DisplayName("Should get run method successfully")
	void shouldGetRunMethodSuccessfully() {

		var method = AnnotationLoader.getRunMethod(ProcedureRun.class);

		assertNotNull(method);
		assertEquals("run", method.getName());
	}

	@Test
	@DisplayName("Should get actions methods successfully")
	void shouldGetActionsMethodsSuccessfully() {

		var methods = AnnotationLoader.getActionMethods(PrincipalPage.class);

		assertNotNull(methods);
		assertEquals(2, methods.size());
	}

	@Test
	@DisplayName("Should get actions value successfully")
	void shouldGetActionValueSuccessfully() {

		var method = Stream.of(PrincipalPage.class.getDeclaredMethods()).findFirst().orElse(null);
		var value = AnnotationLoader.getActionValue(method);

		assertNotNull(value);
		assertEquals("This is a action", value);
	}

	@Test
	@DisplayName("Should get page object successfully")
	void shouldGetPageObjectSuccessfully() {

		var page = AnnotationLoader.getPagesObject(ProcedureRun.class);

		assertNotNull(page);
		assertEquals(1, page.size());
	}

	@Test
	@DisplayName("Should get procedure script successfully")
	void shouldGetProcedureScriptSuccessfully() {

		var procedures = AnnotationLoader.getProceduresScript(UC01TC01_Script.class, Procedure.class);

		assertNotNull(procedures);
		assertEquals(2, procedures.size());
	}

	@Test
	@DisplayName("Should get pages successfully")
	void shouldGetPagesSuccessfully() {

		var pages = AnnotationLoader.getPages(ProcedureRun.class);

		assertNotNull(pages);
		assertEquals(1, pages.size());
	}

	@ParameterizedTest
	@MethodSource("getClazzSql")
	@DisplayName("Should get sql filepath successfully")
	void shouldGetSqlFilespathSuccessfully(Class<?> clazz) {

		var sqls = AnnotationLoader.getSqlPaths(clazz);

		assertNotNull(sqls);
		assertEquals(1, sqls.size());
	}

	private static Stream<Arguments> getClazzSql() {
		return Stream.of(
				Arguments.of(UC10_SuiteWithSql.class),
				Arguments.of(UC10TC02_ScriptWithSql.class));
	}

	@Dataset("path/to/none.csv")
	@Ignore
	static class TestAnnotationLoader {

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