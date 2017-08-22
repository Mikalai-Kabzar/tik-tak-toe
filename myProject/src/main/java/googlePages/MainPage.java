package googlePages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.shaman.common.Services.WaitService;

import googlePages.tikTakToeGame.TikTakToeGamePage;

public class MainPage extends AbstractPage {

	private static final By MAIN_INPUT_XPATH = By.xpath(".//*[@id='lst-ib']");

	/**
	 * Look for some game by request.
	 * 
	 * @return {@link MainPage} instance.
	 */
	public MainPage lookFor(String request) {
		LOG.info("Look for game bt request '" + request + "'.");

		LOG.debug("Look for input.");
		WebElement mainInput = WaitService.findElement(MAIN_INPUT_XPATH);

		LOG.debug("Perform single click on input.");
		mainInput.click();

		LOG.debug("Fill input.");
		mainInput.sendKeys(request);

		LOG.debug("Perform 'Enter' click.");
		mainInput.sendKeys(Keys.ENTER);
		return this;
	}

	/**
	 * Go to Tik-Tak-Toe game page.
	 * 
	 * @return {@link TikTakToeGamePage} instance.
	 */
	public TikTakToeGamePage goToTikTakToeGamePage() {

		lookFor(TikTakToeGamePage.GAME_REQUEST);
		return new TikTakToeGamePage();

	}

	@Override
	public void waitForPageLoading() {
		WaitService.waitUntilElementToBeVisible(MAIN_INPUT_XPATH);
	};

}
