package com.shaman.common.Services;

import googlePages.MainPage;

public class NavigationService {

	public static MainPage getMainPage() {
		MainPage mainPage = new MainPage();
		mainPage.waitForPageLoading();
		return mainPage;
	}
}
