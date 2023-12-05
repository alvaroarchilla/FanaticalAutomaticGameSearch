package com.fanatical.base;

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
import java.io.BufferedWriter;
import java.io.File;
import java.time.Duration;
import org.openqa.selenium.interactions.Actions;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.PrintWriter;
import org.junit.Test;

import com.fanatical.pages.SteamGamePage;


import org.openqa.selenium.WebDriver;


public class BaseTest {

	protected WebDriver driver;

	@Before
	public void setUp() {
		BrowserDriverFactory factory = new BrowserDriverFactory("chrome");
		driver = factory.createDriver();

		driver.manage().window().maximize();
		driver.get("https://store.steampowered.com/app/268910/Cuphead/");
	}

	@After
	public void tearDown() {
		System.out.println("Close driver");
		// Close browser
	//	driver.quit();
	}

}