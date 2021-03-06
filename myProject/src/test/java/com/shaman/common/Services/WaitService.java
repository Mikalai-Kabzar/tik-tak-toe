package com.shaman.common.Services;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.shaman.common.driver.Driver;

public class WaitService {

	protected static final Logger LOG = Logger.getLogger(WaitService.class);

	public static final int TIMEOUT_SECONDS = 30;
	public static final int SMALL_TIMEOUT_MILLISECONDS = 200;
	public static final int PULLING_EVERY_MILLISECONDS = 500;
	public static final int LONG_TIMEOUT_SECONDS = 60;
	public static final int SMALL_POOLING_EVERY = PULLING_EVERY_MILLISECONDS / 4;

	public static boolean isElementExist(String locator) {
		try {
			Driver.getDriver().findElement(By.xpath(locator));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void waitForElementExist(String locator) {
		new WebDriverWait(Driver.getDriver(), TIMEOUT_SECONDS).ignoring(NoSuchElementException.class)
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
	}

	public static WebElement findElement(String locator) {
		return Driver.getDriver().findElement(By.xpath(locator));
	}

	public static WebElement findElement(By by) {
		return Driver.getDriver().findElement(by);
	}

	public static List<WebElement> findElements(By by) {
		return Driver.getDriver().findElements(by);
	}

	public static List<WebElement> findElements(String locator) {
		return Driver.getDriver().findElements(By.xpath(locator));
	}

	public static void waitUntilElementToBeVisible(WebElement element) {
		WebDriverWait wdwait = new WebDriverWait(Driver.getDriver(), TIMEOUT_SECONDS);
		wdwait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void waitUntilElementToBeVisible(String locator) {
		waitUntilElementToBeVisible(By.xpath(locator));
	}

	public static void waitUntilElementToBeVisible(By locator) {
		WebDriverWait wdwait = new WebDriverWait(Driver.getDriver(), TIMEOUT_SECONDS);
		wdwait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public static void waitForElementToClickable(By locator) {
		WebDriverWait wdwait = new WebDriverWait(Driver.getDriver(), TIMEOUT_SECONDS);
		wdwait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static void waitForElementToClickable(WebElement element) {
		WebDriverWait wdwait = new WebDriverWait(Driver.getDriver(), TIMEOUT_SECONDS);
		wdwait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void sleep(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}