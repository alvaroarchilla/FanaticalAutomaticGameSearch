package com.fanatical.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.fanatical.base.BaseTest;

public class SteamGamePage extends BasePageObject {

	public By categoryElement = By.xpath("//*[@id=\"category_block\"]/div/a");
	public By priceElement = By.className("game_purchase_price");
	public By priceDiscountedElement = By.className("discount_final_price");	
	
	public By gameLabel = By.xpath(
			"//html/body/div[1]/div[7]/div[6]/div[1]/div[3]/div[1]/div[4]/div[1]/div[2]/div[1]/div/div[4]/div[2]/div[2]/a");
	public By gameName = By.xpath("//*[@id=\"appHubAppName\"]");
	public By gameReleaseDate = By.xpath("//*[@id='game_highlights']/div[1]/div/div[3]/div[2]/div[2]");
	public By gameDeveloperName = By.xpath(
			"/html/body/div[1]/div[7]/div[6]/div[1]/div[3]/div[1]/div[4]/div[1]/div[2]/div[1]/div/div[3]/div[3]/div[2]/a");
	public By gameEditorName = By.xpath(
			"/html/body/div[1]/div[7]/div[6]/div[1]/div[3]/div[1]/div[4]/div[1]/div[2]/div[1]/div/div[3]/div[4]/div[2]/a");

	public By reviewGeneral = By.xpath("//*[@id=\"userReviews\"]/div/div[2]");
	public By modalCategoryLocator = By.xpath("//*[@id=\"app_tagging_modal\"]/div/div[2]/div/div/a");
	public By rejectCookiesButton = By.xpath("//*[@id=\"rejectAllButton\"]/span");
	public By notAuthorisedButton = By.xpath("/html/body/div[4]/div[3]/div/div[2]/div/span");
	
	List<WebElement> categoryItems = driver.findElements(categoryElement);
	List<WebElement> gameLabels = driver.findElements(gameLabel);
	List<WebElement> modalCategories = driver.findElements(modalCategoryLocator);

	public SteamGamePage(WebDriver driver) {
		super(driver);
	}

	public void clickRejectCookiesSteam() {
		try {

			if(isClickable(driver.findElement(rejectCookiesButton))){
				System.out.println("Clicking RejectCookiesButton");
				driver.findElement(rejectCookiesButton).click();
			}

		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	public void clickNotAuthorisedButton() {
		try {
			if(isElementPresent(notAuthorisedButton)){
					if(isClickable(driver.findElement(notAuthorisedButton))){
						System.out.println("Clicking NotAuthorisedButton");
						driver.findElement(notAuthorisedButton).click();
					}
			}
		} catch (Exception e) {
			System.out.println("An error occurred on clickNotAuthorisedButton().");
			e.printStackTrace();
		}
	}
	

	public void ageChildControl() {
		try {
			driver.findElement(By.id("ageYear")).click();

			WebElement dropdown = driver.findElement(By.id("ageYear"));
			dropdown.findElement(By.xpath("//option[. = '1970']")).click();

			driver.findElement(By.cssSelector("#view_product_page_btn > span")).click();
		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public String showPrice() {
		String datastring = "\n > Price: ";
		try {
			if(driver.findElement(priceElement).isDisplayed()) {
				System.out.println("Regular price: ");
				datastring = datastring.concat(driver.findElement(priceElement).getText());
			}
			else if(driver.findElement(priceDiscountedElement).isDisplayed()) {
				System.out.println("Discounted price: ");
				datastring = datastring.concat(driver.findElement(priceDiscountedElement).getText());
			}
			datastring = datastring.concat(driver.findElement(priceElement).getText());
		} catch (Exception e) {
			System.out.println("An error occurred on showPrice()");
			e.printStackTrace();
		}
		return datastring;

	}

	public String showCategories() {
		String datastring = "\n > Categories (" + categoryItems.size() + "): ";

		for (WebElement categoryItem : categoryItems) {
			datastring = datastring.concat(categoryItem.getText() + " - ");
		}
		return datastring;
	}

	public String showGameLabel() {
		String datastring = "\n > GameLabel (" + gameLabels.size() + "): ";
		driver.findElement(By.xpath("//*[@id=\"glanceCtnResponsiveRight\"]/div[2]/div[2]/div")).click();
		List<WebElement> modalCategories = driver.findElements(modalCategoryLocator);
		for (WebElement modalCategory : modalCategories) {
			datastring = datastring.concat(modalCategory.getText() + " - ");
		}
		return datastring;
	}

	public String showGameData() {
		try {
		String datastring = "\n\n Game Name: " + driver.findElement(gameName).getText() + " - "
				+ driver.getCurrentUrl();
		datastring = datastring.concat("\n Review > " + driver.findElement(reviewGeneral).getText());
		datastring = datastring.concat("\n Release Date > " + driver.findElement(gameReleaseDate).getText());
		datastring = datastring.concat("\n Developer > " + driver.findElement(gameDeveloperName).getText());
		datastring = datastring.concat("\n Editor > " + driver.findElement(gameEditorName).getText());
		return datastring;
		}
		catch(Exception e) {
			return "";
		}
	}
	
	public String getGenericData(SteamGamePageData selectedData) {
		
		try {
			switch(selectedData) {
				case GAME_NAME:
					return driver.findElement(gameName).getText();
				case PRICE:
					return showPrice();
				case REVIEWS:
					return driver.findElement(reviewGeneral).getText();
				case CATEGORIES:
					return showCategories();
				case GAME_LABEL:
					return showGameLabel();
				case RELEASE_DATE:
					return driver.findElement(gameReleaseDate).getText();
				case EDITOR:
					return driver.findElement(gameEditorName).getText();
				case DEVELOPER:
					return driver.findElement(gameDeveloperName).getText();
				case URL:
					return driver.getCurrentUrl();
				default:
					throw new Exception();
			}
		
		} catch (Exception e) {
			return "Cannot retrieve '" + selectedData.toString() + "' info from steam game!";
		}
	}

	public String showAllGameData() {
		String datastring = showGameData();
		datastring = datastring.concat(showPrice());
		datastring = datastring.concat(showCategories());
		datastring = datastring.concat(showGameLabel());
		System.out.print(datastring);

		return datastring;
	}
	
	public enum SteamGamePageData{
		GAME_NAME, URL, PRICE, REVIEWS, CATEGORIES, GAME_LABEL, RELEASE_DATE, EDITOR, DEVELOPER;
	}

}
