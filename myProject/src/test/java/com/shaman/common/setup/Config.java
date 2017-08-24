package com.shaman.common.setup;

import com.shaman.common.reporter.CustomLogger;

import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

@Resource.Classpath("test.properties")
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

	@Property("browser")
	private String browser;

	public String getStartPage() {
		String host = "http://" + startPage;
		return host;
	}

	public long getImplicityTimeOut() {
		return implicityTimeOut;
	}

	private static Config config = null;

	private Config() {
		PropertyLoader.populate(this);
	}

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

	public String getBrowser() {
		return browser;
	}

}
