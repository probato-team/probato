package org.probato.browser;

import java.util.Comparator;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

import org.probato.entity.model.Browser;
import org.probato.entity.type.BrowserType;
import org.probato.exception.IntegrityException;

public interface BrowserService {

	String BROWSER_IMPLEMENTATION_NOT_FOUND = "Browser implementation not found: {0}";

	Object driver();

	String getBrowserDescription();

	String getBrowserVersion();

	void run();

	void destroy();

	boolean accepted(BrowserType type);

	void browser(Browser browser);

	static BrowserService getInstance(Browser browser) {
		return ServiceLoader.load(BrowserService.class)
				.stream()
				.map(Provider::get)
				.filter(service -> service.accepted(browser.getType()))
				.sorted(Comparator.comparing(service -> service.getClass().getPackageName().equals(BrowserService.class.getClass().getPackageName()), Comparator.reverseOrder()))
				.map(service -> {
					service.browser(browser);
					return service;
				})
				.findFirst()
				.orElseThrow(() -> new IntegrityException(BROWSER_IMPLEMENTATION_NOT_FOUND, browser.getType()));
	}

}