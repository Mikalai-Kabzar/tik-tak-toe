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
		// WebDriver driver = DriverFactory.getDriver(DriverType.IEXPLORER);
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
			LOG.warn("There is no '.yaml' file in project.");
		}
		if (reader != null) {
			try {
				object = reader.read();
			} catch (YamlException e) {
				e.printStackTrace();
				throw new RuntimeException("Error during reading '.yaml' file");
			}

			@SuppressWarnings("unchecked")
			HashMap<String, Object> map = (HashMap<String, Object>) object;
			map.forEach((key, value) -> {
				boolean isAcessiable = true;
				Field field = null;
				try {
					field = this.getClass().getDeclaredField(key);
					isAcessiable = field.isAccessible();
					if (!isAcessiable) {
						field.setAccessible(true);
					}
					String stringValue = value.toString();
					switch (field.getGenericType().toString()) {
					case "int":
						field.setInt(this, Integer.parseInt(stringValue));
						break;
					case "boolean":
						field.setBoolean(this, Boolean.parseBoolean(stringValue));
						break;
					case "float":
						field.setFloat(this, Float.parseFloat(stringValue));
						break;
					case "byte":
						field.setByte(this, Byte.parseByte(stringValue));
						break;
					case "short":
						field.setShort(this, Short.parseShort(stringValue));
						break;
					case "long":
						field.setLong(this, Long.parseLong(stringValue));
						break;
					case "double":
						field.setDouble(this, Double.parseDouble(stringValue));
						break;
					default:
						field.set(this, value);
					}
				} catch (SecurityException | IllegalArgumentException | IllegalAccessException
						| NoSuchFieldException e2) {
					e2.printStackTrace();
					throw new RuntimeException(
							"Error during setting values from '.yaml' file to test class variables.");
				} finally {
					if (!isAcessiable) {
						field.setAccessible(false);
					}
				}
			});
		}
	}
}
