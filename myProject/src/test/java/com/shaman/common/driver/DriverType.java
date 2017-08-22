package com.shaman.common.driver;

/**
 * Enumeration of available driver types
 */
public enum DriverType {
	/**
	 * Firefox
	 */
	FIREFOX("firefox", "firefox.exe"),

	/**
	 * Chrome
	 */
	CHROME("chrome", "chrome.exe"),

	/**
	 * Internet Explorer
	 */
	IEXPLORER("ie", "iexplore.exe");

	private String name;
	private String process;

	private DriverType(String name, String process) {
		this.name = name;
		this.process = process;
	}

	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Return name of browser's process in Windows
	 *
	 * @return
	 */
	public String getProcessName() {
		return process;
	}

	public static DriverType valueOfString(String name) {
		DriverType driverType = DriverType.CHROME;
		if (name.equalsIgnoreCase("firefox")) {
			driverType = DriverType.FIREFOX;
		}
		if (name.equalsIgnoreCase("ie")) {
			driverType = DriverType.IEXPLORER;
		}
		if (name.equalsIgnoreCase("chrome")) {
			driverType = DriverType.CHROME;
		}
		return driverType;
	}
}
