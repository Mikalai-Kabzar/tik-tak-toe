package com.shaman.googleTests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shaman.common.basetest.BaseTest;

import googlePages.MainPage;
import googlePages.tikTakToeGame.TikTakToeGamePage;
import googlePages.tikTakToeGame.Gamers.Gamer;
import googlePages.tikTakToeGame.enums.Complexity;

public class TikTakToeTest extends BaseTest {

	private Gamer doodle;
	private Gamer child;
	private Gamer genius;
	private TikTakToeGamePage tikTakToeGamePage;
	private int attempts;

	@BeforeClass
	public void goToTikTakToeGame() {
		MainPage mainPage = MainPage.getMainPage();
		tikTakToeGamePage = mainPage.goToTikTakToeGamePage();
		doodle = tikTakToeGamePage.crateDoodleGamer();
		child = tikTakToeGamePage.crateChildGamer();
		genius = tikTakToeGamePage.crateGeniusGamer();
	}

	@DataProvider
	public Object[][] dataForTest() {
		return new Object[][] { { doodle, Complexity.HARD, attempts, 0 },
				{ genius, Complexity.EASY, attempts, attempts }, { child, Complexity.MEDIUM, attempts, 1 } };
	}

	// @DataProvider
	// public Object[][] dataForTest() {
	// return new Object[][] { { doodle, Complexity.HARD, 0 } };
	// }

	@Test(dataProvider = "dataForTest")
	public void tikTakToeTest(Gamer gamer, Complexity complexity, int numberOfGames, int minimumWins) {
		// System.out.println("numberOfGames = " + attempts);
		// System.out.println("list = " + list.toString());
		// System.out.println("attempts = " + attempts);
		tikTakToeGamePage.selectComplexity(complexity);
		tikTakToeGamePage.newGame();
		tikTakToeGamePage.playGame(gamer, numberOfGames);
		tikTakToeGamePage.printStat();
		int actualWinsX = TikTakToeGamePage.getWinsX();
		Assert.assertTrue(actualWinsX >= minimumWins,
				"Actual wins 'X' count '" + actualWinsX + "' less than expected '" + minimumWins + "'.");
	}
}