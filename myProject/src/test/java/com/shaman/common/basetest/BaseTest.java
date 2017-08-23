package com.shaman.common.basetest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
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
		readYaml();
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
		Driver.killDriver();
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

	/**
	 * Read .yaml file and set variables to test class fields.
	 */

	private void readYaml() {
		YamlReader reader = null;
		Object object = null;
		String[] classNamePath = this.getClass().getName().split("\\.");
		String className = classNamePath[classNamePath.length - 1];
		try {
			reader = new YamlReader(new FileReader(this.getClass().getResource(".").getPath() + className + ".yaml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			object = reader.read();
		} catch (YamlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HashMap<String, Object> map = (HashMap<String, Object>) object;
		StringBuilder fieldNames = new StringBuilder();
		for (Field field : this.getClass().getDeclaredFields()) {
			fieldNames.append(field.getName()).append("||");
		}
		map.forEach((key, value) -> {
			boolean isAcessiable = true;
			Field field = null;
			try {
				if (fieldNames.indexOf(key) != -1) {
					field = this.getClass().getDeclaredField(key);
					isAcessiable = field.isAccessible();
					if (!isAcessiable) {
						field.setAccessible(true);
					}
					switch (field.getGenericType().toString()) {
					case "int":
						field.setInt(this, Integer.parseInt(value.toString()));
						break;

					default:
						field.set(this, value);
					}
				}

			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} finally {
				if (!isAcessiable) {
					field.setAccessible(false);
				}
			}
		});
	}
}
