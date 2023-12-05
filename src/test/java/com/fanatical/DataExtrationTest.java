package com.fanatical;

import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.*;
import java.io.File;
import java.time.Duration;
import org.openqa.selenium.interactions.Actions;

public class DataExtrationTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	JavascriptExecutor js;

	@Before
	public void setUp() throws Exception {
		 System.setProperty("webdriver.chrome.driver",
		 "src/main/resources/chromedriver.exe");
		 driver = new ChromeDriver();
		//System.setProperty("webdriver.edge.driver", "src/main/resources/msedgedriver.exe");
		//driver = new EdgeDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		js = (JavascriptExecutor) driver;
	}

	@Test
	public void testUntitledTestCase() throws Exception {

		By allowAlert = By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div/button[1]");
		By gamesCategory = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/nav/div/a[2]");
		By ebooksCategory = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/nav/div/a[3]");
		By softwareCategory = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/nav/div/a[4]");
		By audioCategory = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/nav/div/a[5]");

		By closeNewsletter = By.xpath("//*[@id=\"lightbox-cb4fd363-c404-47cc-926e-16a282f0636e-1647367590861\"]/div");

		By bundleItemLocator = By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div/div/div/div/div[2]");
		By bundleItemLocator2 = By.xpath("//*[@id=\"root\"]/div/div/div/div/div[2]/div/div");

		// Navigate to website
		driver.get("https://www.fanatical.com/en/bundle");
		driver.manage().window().maximize();

		// Create the wait
		WebDriverWait wait = new WebDriverWait(driver, 2);

		// Click on alerts
		//driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div/button[1]")).click();

		// wait.until(ExpectedConditions.visibilityOf(driver.findElement(allowAlert)));
		driver.findElement(allowAlert).click();
		driver.findElement(gamesCategory).click();


		List<WebElement> bundleItems = driver.findElements(bundleItemLocator);
		System.out.print(bundleItems.size());

		List<WebElement> bundleItems2 = driver.findElements(bundleItemLocator2);
		System.out.print(bundleItems2.size());

		Actions actions = new Actions(driver);

		String firstTab = driver.getWindowHandle();
	
		for (WebElement bundleItem : bundleItems2) {

			actions.moveByOffset(150, 500).click().build().perform();
			if (isClickable(bundleItem)) {

				if (isElementPresent(closeNewsletter)) {
					driver.findElement(closeNewsletter).click();
				}
			//	System.out.print("Clicable:  "+ isClickable(bundleItem) + "\n");
				System.out.print("Posición:  " + bundleItem.getLocation() + "\n");
			//	System.out.print("Displayed:  "+ bundleItem.isDisplayed() + "\n\n");

				//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", bundleItem);

				((JavascriptExecutor) driver).executeScript("window.open('https://www.fanatical.com/en/bundle/games');");
				Thread.sleep(2000);
				// Create an object of Actions class and pass reference variable driver as a
				// parameter to its constructor.
				//actions.moveToElement(bundleItem);
				//actions.moveToElement(bundleItem).click().build().perform();
				
				actions.moveByOffset(1500, 500).click().build().perform();
				Thread.sleep(2000);
		//		actions.click(bundleItem);
		//		actions.build().perform();
			/*	
				driver.navigate().back();
				Thread.sleep(2000);
				driver.close();*/
				//driver.switchTo(firstTab);
			//	String firstWindow = driver.getWindowHandle();
				// switchToWindowWithTitle(driver, firstTab);

				// driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div[2]")).click();

				// steamDataExtraction();

				// driver.findElement(By.className(bundleItem.getAttribute("class")));
				// driver.switchTo().window(firstWindow);
				// driver.switchTo().window(firstWindow);
				// navigateFirstWindow(driver);
				// switchToWindowWithTitle(driver, firstTab);

//((JavascriptExecutor) driver).executeScript("window.history.back();");

				// driver.get("https://www.fanatical.com/en/pick-and-mix/build-your-own-very-positive-bundle-2");
				// steamDataExtraction();
				// driver.get("https://www.fanatical.com/en/pick-and-mix/build-your-own-tiger-bundle");
				// steamDataExtraction();

			}

			/*
			 * if (isElementPresent(By.cssSelector(
			 * "div.card-container.col-6.col-sm-4.col-md-6.col-lg-4"))) { //
			 * if(bundleItem.isDisplayed()) { bundleItem.click();
			 * 
			 * System.out.print(bundleItem.toString() + "\n");
			 * 
			 * }
			 */

		}

	}

	@After
	public void tearDown() throws Exception {
		// driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			System.out.print("Elemento no presente \n");
			return false;
		}
	}

	public boolean isClickable(WebElement webe) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.elementToBeClickable(webe));
			return true;
		} catch (Exception e) {
			System.out.print("Elemento no clickable \n");

			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	public WebDriver navigateFirstWindow(WebDriver driver) {
		String firstWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> windowsIterator = allWindows.iterator();
		driver.switchTo().window(windowsIterator.next());
		while (windowsIterator.hasNext()) {
			String windowHandle = windowsIterator.next().toString();
			if (!windowHandle.equals(firstWindow)) {
				driver.switchTo().window(windowHandle);
			}
		}
		return driver;
	}

	public WebDriver switchToWindowWithTitle(WebDriver driver, String expectedTitle) {

		String firstWindow = driver.getWindowHandle();

		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> windowsIterator = allWindows.iterator();

		while (windowsIterator.hasNext()) {
			String windowHandle = windowsIterator.next().toString();
			if (!windowHandle.equals(firstWindow)) {
				driver.switchTo().window(windowHandle);
				if (driver.getTitle().equals(expectedTitle)) {
					break;
				}

			}
		}
		return driver;
	}

	void steamDataExtraction() {
		List<WebElement> webItems = driver
				.findElements(By.xpath("//*[@id=\"root\"]/div/div[2]/main/div/section[2]/div"));
		for (WebElement webItem : webItems) {
			
			
			webItem.click();

			if (isElementPresent(By.linkText("View on Steam"))) {
				// System.out.println(By.linkText("View on Steam").toString());
				driver.findElement(By.linkText("View on Steam")).click();
			}

			if (isElementPresent(By.linkText("VIEW"))) {
				// System.out.println(By.linkText("View on Steam").toString());
				driver.findElement(By.linkText("VIEW")).click();
			}
		}

		String firstWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> windowsIterator = allWindows.iterator();

		while (windowsIterator.hasNext()) {
			String windowHandle = windowsIterator.next().toString();
			if (!windowHandle.equals(firstWindow)) {
				driver.switchTo().window(windowHandle);

				if (isElementPresent(By.xpath("//*[@id=\"rejectAllButton\"]/span"))) {

					driver.findElement(By.xpath("//*[@id=\"rejectAllButton\"]/span")).click();
				}
				// Windows Iteration
				if (isElementPresent(By.linkText("Coop. a pantalla (com)partida"))
						|| isElementPresent(By.linkText("JcJ a pantalla (com)partida"))) {
					System.out.print("\n\n" + driver.findElement(By.xpath("//*[@id=\"appHubAppName\"]")).getText()
							+ " - " + driver.findElement(By.xpath("//*[@id=\"userReviews\"]/div/div[2]")).getText()
							+ " - " + driver.getCurrentUrl() + " \n");

					List<WebElement> categoryItems = driver.findElements(By.xpath("//*[@id=\"category_block\"]/div/a"));

					System.out.print(" > Categorías(" + categoryItems.size() + "): ");
					for (WebElement categoryItem : categoryItems) {
						System.out.print(categoryItem.getText() + " - ");
					}
				}
			}
			driver.close();

		}
		return;
	}

}
