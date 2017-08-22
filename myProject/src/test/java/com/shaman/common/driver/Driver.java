package com.shaman.common.driver;

import org.openqa.selenium.WebDriver;

public class Driver {

	private static WebDriver driver;

	private Driver() {
	}

	public static WebDriver getDriver() {
		if (driver == null) {
			driver = DriverFactory.getDriver();
		}
		return driver;
	}
}
