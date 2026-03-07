package org.probato.test.support;

import org.testcontainers.DockerClientFactory;

public final class DockerSupport {

	private DockerSupport() {}

	public static boolean isDockerAvailable() {
		try {
			DockerClientFactory.instance().client();
			return true;
		} catch (Throwable ex) {
			return false;
		}
	}

}