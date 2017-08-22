package com.shaman.googleTests.tests;

import org.testng.annotations.Test;

import com.shaman.common.basetest.BaseTest;

import googlePages.MainPage;
import googlePages.tikTakToeGame.TikTakToeGamePage;
import googlePages.tikTakToeGame.Gamers.Gamer;

public class TikTakToeTest extends BaseTest {

	@Test
	public void tikTakToeTest() {

		// WaitService.sleep(1000);
		MainPage mainPage = MainPage.getMainPage();
		TikTakToeGamePage tikTakToeGamePage = mainPage.goToTikTakToeGamePage();
		Gamer genius = tikTakToeGamePage.crateGeniusGamer();
		tikTakToeGamePage.setEasyComplexity();
		tikTakToeGamePage.setHardComplexity();
		tikTakToeGamePage.setMediumComplexity();
		tikTakToeGamePage.playGame(genius, 1);
		tikTakToeGamePage.printStat();
	}

}
