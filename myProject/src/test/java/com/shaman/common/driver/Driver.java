package com.shaman.common.driver;

import org.openqa.selenium.WebDriver;

import com.shaman.common.setup.Config;

public class Driver {

	private static WebDriver driver;

	private Driver() {
	}

	public static WebDriver getDriver() {
		if (driver == null) {
			driver = DriverFactory.getDriver(DriverType.valueOfString(Config.getInstance().getBrowser()));
		}
		return driver;
	}

	// public static WebDriver getDriver(DriverType driverType) {
	// if (driver == null) {
	// driver = DriverFactory.getDriver(driverType);
	// }
	// return driver;
	// }

	public static void killDriver() {
		driver = null;
	}

}
