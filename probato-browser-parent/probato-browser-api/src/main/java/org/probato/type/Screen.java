package org.probato.type;

/**
 * Represents the target screen (monitor) where the browser window
 * should be displayed in multi-monitor environments.
 */

public enum Screen {

	/**
	 * The primary (main) screen.
	 */
	PRIMARY,

	/**
	 * The secondary screen.
	 */
	SECONDARY,

	;

	/**
	 * Resolves a {@link Screen} from a string value, ignoring case.
	 *
	 * <p>If the provided value does not match any known screen name,
	 * {@link #PRIMARY} is returned as a default.</p>
	 *
	 * @param value the textual representation of the screen
	 * @return the corresponding {@code Screen}, or {@code PRIMARY} if no match is found
	 */
	public static Screen fromString(String value) {
		for (var item : values()) {
			if (item.name().equalsIgnoreCase(value)) {
				return item;
			}
		}
		return PRIMARY;
	}

}