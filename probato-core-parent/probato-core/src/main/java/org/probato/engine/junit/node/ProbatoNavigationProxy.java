package org.probato.engine.junit.node;

import java.awt.Desktop;
import java.nio.file.Paths;

public final class ProbatoNavigationProxy  {

	private ProbatoNavigationProxy () {}

	public static void configuration() {
		try {
			Desktop.getDesktop().open(
				Paths.get("src/test/resources/configuration.yml").toFile()
			);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
