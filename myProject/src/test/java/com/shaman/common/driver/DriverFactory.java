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
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

public class DriverFactory {

	private static final CustomLogger LOG = CustomLogger.getLogger(DriverFactory.class);

	private static final String CHROME_VERSION = "2.31";
	private static final String IE64_VERSION = System.getProperty("ie64.exe.ver");
	private static final String IE_VERSION = System.getProperty("ie32.exe.ver");

	private static final String WINDOWS_CHROME_DRIVER_PATH = System.getProperty("user.home")
			+ "/.m2/repository/drivers/chromedriver/2.29/chromedriver-" + CHROME_VERSION + ".exe";

	private static final String WINDOWS_IE_DRIVER_PATH = System.getProperty("user.home")
			+ "/.m2/repository/drivers/iedriverserver32/" + IE_VERSION + "/iedriverserver32-" + IE_VERSION + ".exe";

	@SuppressWarnings("unused")
	private static final String WINDOWS_IE64_DRIVER_PATH = System.getProperty("user.home")
			+ "/.m2/repository/drivers/iedriverserver64/" + IE64_VERSION + "/iedriverserver64-" + IE64_VERSION + ".exe";

	/**
	 * Create driver.
	 *
	 * @param driverType
	 *            Type of driver.
	 * @return Created WebDriver.
	 */
	public static WebDriver getDriver() {
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
			driver = new FirefoxDriver();
			break;
		case IEXPLORER:
			if (!config.isWebDriverManager()) {
				LOG.debug("Setting system property webdriver.ie.driver = %s", WINDOWS_IE_DRIVER_PATH);
				System.setProperty("webdriver.ie.driver", WINDOWS_IE_DRIVER_PATH);
				LOG.debug("Property has been set");
			} else {
				LOG.debug("Setting driver properties via WebDriverManager");
				InternetExplorerDriverManager.getInstance().version(IE_VERSION).setup();
				LOG.debug("Property has been set");
			}
			LOG.debug("Setting capabilities");
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
					false);
			ieCapabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, config.isIERequireFocus());
			LOG.debug("Gettting driver instance");
			driver = new InternetExplorerDriver(ieCapabilities);
			break;
		case CHROME:
		default:
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
	 * @param url
	 * @return
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
