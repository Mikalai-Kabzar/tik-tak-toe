package googlePages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.shaman.common.driver.Driver;

abstract public class AbstractPage {

	protected static final Logger LOG = Logger.getLogger(AbstractPage.class);

	protected WebDriver driver = Driver.getDriver();

	/**
	 * Wait for page loading.
	 */
	public abstract void waitForPageLoading();
}
