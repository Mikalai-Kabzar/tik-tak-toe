package com.shaman.common.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.shaman.common.reporter.CustomLogger;
import com.shaman.common.setup.Config;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class DriverFactory {

	private static final CustomLogger LOG = CustomLogger.getLogger(DriverFactory.class);

	private static final String DRIVERS_PATH = new java.io.File("").getAbsolutePath() + "/src/test/resources/drivers/";
	private static final String CHROME_DRIVER_NAME = "chromedriver.exe";
	private static final String IE_DRIVER_NAME = "IEDriverServer.exe";
	private static final String FIREFOX_DRIVER_NAME = "geckodriver.exe";

	private static final String CHROME_VERSION = "2.31";
	private static final String IE64_VERSION = System.getProperty("ie64.exe.ver");
	private static final String IE_VERSION = System.getProperty("ie32.exe.ver");

	private static final String WINDOWS_CHROME_DRIVER_PATH = DRIVERS_PATH + CHROME_DRIVER_NAME;
	private static final String WINDOWS_IE_DRIVER_PATH = DRIVERS_PATH + IE_DRIVER_NAME;
	private static final String WINDOWS_FIREFOX_DRIVER_PATH = DRIVERS_PATH + FIREFOX_DRIVER_NAME;

	//
	// private static final String WINDOWS_IE_DRIVER_PATH =
	// System.getProperty("user.home")
	// + "/.m2/repository/drivers/iedriverserver32/" + IE_VERSION +
	// "/iedriverserver32-" + IE_VERSION + ".exe";
	//
	// @SuppressWarnings("unused")
	// private static final String WINDOWS_IE64_DRIVER_PATH =
	// System.getProperty("user.home")
	// + "/.m2/repository/drivers/iedriverserver64/" + IE64_VERSION +
	// "/iedriverserver64-" + IE64_VERSION + ".exe";

	/**
	 * Create driver.
	 *
	 * @param driverType
	 *            Type of driver.
	 * @return Created WebDriver.
	 */
	public static WebDriver getDriver123456() {
		WebDriver driver = null;
		Config config = Config.getInstance();

		if (!config.isWebDriverManager()) {
			System.setProperty("webdriver.chrome.driver", WINDOWS_CHROME_DRIVER_PATH);
		} else {
			ChromeDriverManager.getInstance().version(CHROME_VERSION).setup();
		}
		System.setProperty("webdriver.chrome.driver", WINDOWS_CHROME_DRIVER_PATH);

		ChromeOptions options = new ChromeOptions();

		options.setExperimentalOption("excludeSwitches", Arrays.asList("ignore-certificate-errors"));
		options.addArguments("--disable-extensions");
		options.addArguments("disable-infobars");
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);

		driver = new ChromeDriver(options);

		LOG.debug("Driver has been created");
		return driver;
	}

	/**
	 * Create driver.
	 *
	 * @param driverType
	 *            Type of driver.
	 * @return Created WebDriver.
	 */
	public static WebDriver getDriver(DriverType driverType) {
		WebDriver driver = null;
		Config config = Config.getInstance();

		switch (driverType) {
		case FIREFOX:
			// System.setProperty("webdriver.gecko.driver", WINDOWS_FIREFOX_DRIVER_PATH);
			// DesiredCapabilities ffCapabilities = DesiredCapabilities.firefox();
			// ffCapabilities.setCapability("marionette", true);
			// driver = new FirefoxDriver(ffCapabilities);

			System.setProperty("webdriver.gecko.driver", WINDOWS_FIREFOX_DRIVER_PATH);
			// System.setProperty("webdriver.firefox.marionette",
			// WINDOWS_FIREFOX_DRIVER_PATH);
			// DesiredCapabilities ffCapabilities = DesiredCapabilities.firefox();
			// ffCapabilities.setCapability("firefox_binary", "C:\\Program Files\\Mozilla
			// Firefox\\firefox.exe");
			// ffCapabilities.setCapability("marionette", true);
			driver = new FirefoxDriver();
			// driver = new MarionetteDriver();

			break;
		case IEXPLORER:
			LOG.debug("Setting capabilities");
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			System.setProperty("webdriver.ie.driver", WINDOWS_IE_DRIVER_PATH);
			LOG.debug("Getting driver instance");
			driver = new InternetExplorerDriver(capabilities);
			break;
		case CHROME:
			// default:
			if (!config.isWebDriverManager()) {
				System.setProperty("webdriver.chrome.driver", WINDOWS_CHROME_DRIVER_PATH);
			} else {
				ChromeDriverManager.getInstance().version(CHROME_VERSION).setup();
			}
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("excludeSwitches", Arrays.asList("ignore-certificate-errors"));
			options.addArguments("--disable-extensions");
			options.addArguments("disable-infobars");
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			driver = new ChromeDriver(options);
			break;
		}

		LOG.debug("Driver has been created");
		return driver;
	}

	/**
	 * Get URL from String
	 * 
	 * @param string
	 *            with url
	 * @return {@Link URL} instance
	 */
	protected static URL getUrlFromString(String url) {
		URL remoteAddress;
		try {
			remoteAddress = new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return remoteAddress;
	}
}