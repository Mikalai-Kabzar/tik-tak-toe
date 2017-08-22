package com.shaman.common.basetest;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.shaman.common.driver.Driver;
import com.shaman.common.setup.Config;

abstract public class BaseTest {
	protected static final Logger LOG = Logger.getLogger(BaseTest.class);
	protected WebDriver driver;

	public WebDriver driver() {
		return driver;
	}

	/**
	 * Driver initialization
	 */
	@BeforeClass
	public void setDriver() {
		setupDriver();
	}

	/**
	 * Create driver and set it as current session
	 */
	protected void setupDriver() {
		driver = createDriver();
	}

	/**
	 * Create driver of required type
	 *
	 * @return {@link WebDriver}
	 */
	public WebDriver createDriver() {
		// WebDriver driver = DriverFactory.getDriver(DriverType.CHROME);
		WebDriver driver = Driver.getDriver();
		driver.manage().timeouts().implicitlyWait(Config.getInstance().getImplicityTimeOut(), TimeUnit.MILLISECONDS);
		driver.manage().window().maximize();
		return driver;
	}

	/**
	 * Navigate to the application URL
	 */
	@BeforeClass(dependsOnMethods = "setDriver")
	public void openUrl() {
		getLoginUrl();
	}

	/**
	 * Close browser
	 */
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		destroyDriver();
		driver = null;
	}

	/**
	 * Destroy driver
	 *
	 * @param driver
	 *            {@link WebDriver} to destroy
	 */
	public void destroyDriver() {
		try {
			driver().manage().deleteAllCookies();
			driver().quit();
		} catch (Exception e) {
			driver().switchTo().alert().dismiss();
			destroyDriver();
		}
	}

	/**
	 * Open login url
	 */
	public void getLoginUrl() {
		String loginUrl = Config.getInstance().getStartPage();
		driver().get(loginUrl);
	}
}
