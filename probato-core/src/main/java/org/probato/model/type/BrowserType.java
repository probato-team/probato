package org.probato.model.type;

public enum BrowserType {

	FIREFOX("Mozilla Firefox"), 
	CHROME("Google Chrome"), 
	EDGE("Microsoft Edge"),
	IE("Microsoft Internet Explorer"),
	SAFARI("Apple Safari"),
	OPERA("Opera"),
	FAKE("Fake")

	;

	private final String descritpion;

	private BrowserType(String descritpion) {
		this.descritpion = descritpion;
	}

	public String descritpion() {
		return descritpion;
	}

	public static String getDescription(String type) {
		String retorno = type;
		for (var item : values()) {
			if (item.name().equals(type)) {
				retorno = item.descritpion();
				break;
			}
		}
		return retorno;
	}

}