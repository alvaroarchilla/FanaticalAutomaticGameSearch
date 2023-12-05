package com.fanatical;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.ArrayList;
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
import java.io.BufferedWriter;
import java.io.File;
import java.time.Duration;
import org.openqa.selenium.interactions.Actions;
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException; // Import the IOException class to handle errors
import java.io.PrintWriter;

import com.fanatical.pages.SteamGamePage;
import com.steam.*;
import com.util.HTMLHandler;

public class Fanatical_Extraction {

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	JavascriptExecutor js;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();
		// System.setProperty("webdriver.edge.driver",
		// "src/main/resources/msedgedriver.exe");
		// driver = new EdgeDriver();
		baseUrl = "https://www.fanatical.com/en/pick-and-mix/prestige-collection-build-your-own-bundle";
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
		By closeCookies = By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div/button[1]");
		By bundleItemLocator = By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div/div/div/div/div[2]");
		By bundleItemLocator2 = By.xpath("//*[@id=\"root\"]/div/div/div/div/div[2]/div/div");

		// Navigate to website
		driver.get(baseUrl);
		driver.manage().window().maximize();

		String pageTitle = driver.getTitle();
		driver.findElement(closeCookies).click();
		try {
			//steamDataExtraction();
			steamDataExtractionToHTML();
		} catch (Exception e) {
			System.out.print(e);
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

	public static void createFile(String fileName) {
		try {
			File myObj = new File(fileName + ".txt");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return;
	}

	public void writeToFile(String inputText, String fileName) {
		try {

			FileWriter myWriter = new FileWriter(fileName + ".txt");

			myWriter.write(inputText);
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	void steamDataExtraction() {
		By nextItemButton = By
				.xpath("//*[@id=\"root\"]/div/div[2]/main/div/section[3]/div[1]/div[1]/div/div[2]/button[2]");

		String fileName = driver.getTitle().substring(0, 30).trim().replaceAll("[^a-zA-Z0-9]", " ").toString()
				+ "... - FanaticalGameBundle";
		createFile(fileName);
		String dataString = "";
		dataString = dataString.concat("Bundle: " + driver.getTitle() + " - " + driver.getCurrentUrl());
		List<WebElement> webItems = driver
				.findElements(By.xpath("//*[@id=\"root\"]/div/div[2]/main/div/section[2]/div"));
		WebElement firstItem = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/main/div/section[2]/div[1]"));
		firstItem.click();
		for (WebElement webItem : webItems) {

			// webItem.click();
			try {

				if (isElementPresent(By.linkText("View on Steam"))) {
					// System.out.println(By.linkText("View on Steam").toString());
					driver.findElement(By.linkText("View on Steam")).click();
				}

				if (isElementPresent(By.linkText("VIEW"))) {
					// System.out.println(By.linkText("View on Steam").toString());
					driver.findElement(By.linkText("VIEW")).click();
				}
				driver.findElement(nextItemButton).click();
			} catch (Exception e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}

		}

		String firstWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> windowsIterator = allWindows.iterator();

		while (windowsIterator.hasNext()) {
			String windowHandle = windowsIterator.next().toString();
			if (!windowHandle.equals(firstWindow)) {
				driver.switchTo().window(windowHandle);
				try {
					if (isElementPresent(By.xpath("//*[@id=\"rejectAllButton\"]/span"))) {

						driver.findElement(By.xpath("//*[@id=\"rejectAllButton\"]/span")).click();
					}
					if (isElementPresent(By.xpath("//html/body/div[1]/div[7]/div[6]/div/div[2]/div/div[1]/div[2]"))) {
						driver.findElement(By.id("ageYear")).click();
						{
							WebElement dropdown = driver.findElement(By.id("ageYear"));
							dropdown.findElement(By.xpath("//option[. = '1970']")).click();
						}
						driver.findElement(By.cssSelector("#view_product_page_btn > span")).click();
					}

					// Windows Iteration

					SteamGamePage steamGamePage = new SteamGamePage(driver);
					steamGamePage.clickNotAuthorisedButton();
					dataString = dataString.concat(steamGamePage.showAllGameData());
				} catch (Exception e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
					return;
				}
			}
			writeToFile(dataString, fileName);
			driver.close();
		}

		return;
	}

	
	void steamDataExtractionToHTML() throws IOException {
		By nextItemButton = By
				.xpath("//*[@id=\"root\"]/div/div[2]/main/div/section[3]/div[1]/div[1]/div/div[2]/button[2]");

		String fileName = driver.getTitle().substring(0, 30).trim().replaceAll("[^a-zA-Z0-9]", " ").toString()
				+ "... - FanaticalGameBundle";
		createFile(fileName);
		String dataString = "";
		dataString = dataString.concat("Bundle: " + driver.getTitle() + " - " + driver.getCurrentUrl());
		List<WebElement> webItems = driver
				.findElements(By.xpath("//*[@id=\"root\"]/div/div[2]/main/div/section[2]/div"));
		WebElement firstItem = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/main/div/section[2]/div[1]"));
		firstItem.click();
		for (WebElement webItem : webItems) {

			// webItem.click();
			try {

				if (isElementPresent(By.linkText("View on Steam"))) {
					// System.out.println(By.linkText("View on Steam").toString());
					driver.findElement(By.linkText("View on Steam")).click();
				}

				if (isElementPresent(By.linkText("VIEW"))) {
					// System.out.println(By.linkText("View on Steam").toString());
					driver.findElement(By.linkText("VIEW")).click();
				}
				driver.findElement(nextItemButton).click();
			} catch (Exception e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}

		}

		String firstWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> windowsIterator = allWindows.iterator();
		List<SteamGamePage> allSteamGamesPages = new ArrayList<>();

		while (windowsIterator.hasNext()) {
			String windowHandle = windowsIterator.next().toString();
			if (!windowHandle.equals(firstWindow)) {
				driver.switchTo().window(windowHandle);
				try {
					if (isElementPresent(By.xpath("//*[@id=\"rejectAllButton\"]/span"))) {

						driver.findElement(By.xpath("//*[@id=\"rejectAllButton\"]/span")).click();
					}
					if (isElementPresent(By.xpath("//html/body/div[1]/div[7]/div[6]/div/div[2]/div/div[1]/div[2]"))) {
						driver.findElement(By.id("ageYear")).click();
						{
							WebElement dropdown = driver.findElement(By.id("ageYear"));
							dropdown.findElement(By.xpath("//option[. = '1970']")).click();
						}
						driver.findElement(By.cssSelector("#view_product_page_btn > span")).click();
					}

					// Windows Iteration

					SteamGamePage steamGamePage = new SteamGamePage(driver);
					steamGamePage.clickNotAuthorisedButton();
					allSteamGamesPages.add(steamGamePage);
				//	dataString = dataString.concat(steamGamePage.showAllGameData());
				} catch (Exception e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
					return;
				}
			}
			//writeToFile(dataString, fileName);
			
			
			
			driver.close();
		}
		
		HTMLHandler htmlHandler = new HTMLHandler(allSteamGamesPages);
		htmlHandler.generate();
		return ;
	}
}
