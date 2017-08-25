package googlePages;

import org.openqa.selenium.WebDriver;

import com.shaman.common.driver.Driver;
import com.shaman.common.reporter.CustomLogger;

abstract public class AbstractPage {

	protected static final CustomLogger LOG = CustomLogger.getLogger(AbstractPage.class);

	protected WebDriver driver = Driver.getDriver();

	/**
	 * Wait for page loading.
	 */
	public abstract void waitForPageLoading();
}
