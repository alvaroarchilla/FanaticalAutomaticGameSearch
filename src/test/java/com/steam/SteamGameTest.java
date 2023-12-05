package com.steam;



import org.junit.Test;

import com.fanatical.base.BaseTest;
import com.fanatical.pages.SteamGamePage;

public class SteamGameTest extends BaseTest{

	
	
	@Test
	public void SteamDataExtraction() {
		

		// open main page
		 SteamGamePage steamGamePage = new SteamGamePage(driver);
		 steamGamePage.clickRejectCookiesSteam();
		 steamGamePage.showAllGameData();

	
	}
	
}
