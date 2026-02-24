package org.probato.service;

import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

import org.probato.browser.BrowserProvider;
import org.probato.browser.BrowserSession;
import org.probato.browser.BrowserSessionData;
import org.probato.exception.IntegrityException;
import org.probato.loader.ConfigurationContext;
import org.probato.model.Browser;

public class BrowserService {

	private static final String BROWSER_PROVIDER_IMPLEMENTATION_NOT_FOUND = "Browser provider implementation not found for {0} type";

	private List<BrowserProvider> providers;

	private BrowserService() {
		load();
	}

	public static BrowserService get() {
		return new BrowserService();
	}

	public BrowserSession createSession(Browser browser) {

		var type = browser.getType();
		var configuration = ConfigurationContext.get();
		var execution = configuration.getExecution();
		var data = new BrowserSessionData(
				browser,
				execution.getEngine(),
				execution.getTarget().getUrl(),
				execution.getScreen(),
				execution.getDelay());

		return providers.stream()
				.filter(provider -> provider.getType().equals(type))
				.findFirst()
				.orElseThrow(() -> new IntegrityException(BROWSER_PROVIDER_IMPLEMENTATION_NOT_FOUND, type.description()))
				.createSession(data);
	}

	private void load() {
		if (Objects.isNull(providers)) {
			providers = ServiceLoader.load(BrowserProvider.class)
					.stream()
					.map(Provider::get)
					.collect(Collectors.toList());
		}
	}

}