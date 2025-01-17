package org.probato.engine;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.loader.Configuration;
import org.probato.test.page.PrincipalPage;

@DisplayName("Test -> PageProxy")
class PageProxyTest {

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Should get procedure script successfully")
	void shouldGetProcedureScriptSuccessfully() throws Throwable {

		Configuration.getInstance(getClass());
		ExecutionContextHolder.clean();

		var page = PrincipalPage.class.newInstance();
		var pageProxy = new PageProxy(page);
		var method = Stream.of(page.getClass().getDeclaredMethods())
				.filter(item -> item.getName().equals("actionWithParam"))
				.findFirst()
				.orElse(null);
		var args = new Object[]{"test"};

		pageProxy.invoke(page, method, args);

		assertNotNull(page);
	}
}