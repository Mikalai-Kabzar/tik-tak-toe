package com.shaman.common.setup;

import java.io.FileInputStream;
import java.util.Properties;

import com.shaman.common.reporter.CustomLogger;

import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

@Resource.Classpath("/src/test/resources/test.properties")
public class Config {

	private static final CustomLogger LOG = CustomLogger.getLogger(Config.class);

	@Property("browser.isWebDriverManager")
	private boolean isWebDriverManager;

	@Property("browser.ie.isRequireFocus")
	private boolean isIERequireFocus;

	@Property("implicityTimeOut")
	private long implicityTimeOut;

	@Property("startPage")
	private String startPage;

	public String getStartPage() {
		String host;
		FileInputStream fis;
		Properties property = new Properties();
		try {
			fis = new FileInputStream("src/test/resources/test.properties");
			property.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		host = "http://" + property.getProperty("startPage");
		return host;
	}

	public long getImplicityTimeOut() {
		return implicityTimeOut;
	}

	private static Config config;

	public static Config getInstance() {
		if (config == null) {
			config = new Config();
		}
		return config;
	}

	public boolean isIERequireFocus() {
		return isIERequireFocus;
	}

	public boolean isWebDriverManager() {
		LOG.info("isWebDriverManager is: " + isWebDriverManager);
		return isWebDriverManager;
	}

	@Property("browser")
	private String browser;

	public String getBrowser() {
		return browser;
	}

}
