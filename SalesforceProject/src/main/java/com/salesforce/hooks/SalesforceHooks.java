package com.salesforce.hooks;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.salesforce.utils.ReadConfigFile;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.concurrent.TimeUnit;

public class SalesforceHooks {
	public static WebDriver driver;
	public static ReadConfigFile rf = new ReadConfigFile("src/main/resources/Resources/Config/config.properties");
	public static ExtentReports repo;
	public static ExtentTest report;
	public static Logger log;

	@Before()
	public void setup(Scenario sc) {

		System.out.println("Harshit mid");
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy HHmmss");
		String formattedDateTime = currentDateTime.format(formatter);
		String fileName = "Reports/Report" + formattedDateTime + ".html";
		repo = new ExtentReports(fileName);
		report = repo.startTest(sc.getName());
		log = Logger.getLogger("Salesfoce User");
		PropertyConfigurator.configure("src/main/resources/Resources/Config/log4j.properties");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		String browser = rf.getProperty("Browser");
		System.out.println(browser);

		switch (browser) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			break;
		default:
			System.out.println("Please provide the correct browser name");
			break;
		}
	}

	@After
	public void tearDown() {
		driver.quit();
		repo.endTest(report);
		repo.flush();

	}

}
