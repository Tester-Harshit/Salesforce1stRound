package com.salesforce.webcommands;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.salesforce.hooks.SalesforceHooks;
import com.salesforce.utils.ReadConfigFile;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebActions {

	public static WebDriver driver;
	public static ReadConfigFile rf ;
	public static ExtentTest report;
	public static Logger log;
	WebDriverWait wait;
	Actions actions;

	public WebActions() {
		driver = SalesforceHooks.driver;
		report = SalesforceHooks.report;
		log = SalesforceHooks.log;
		wait = new WebDriverWait(driver, 30);
		actions = new Actions(driver);
		rf =SalesforceHooks.rf;
	}

	/**
	 *Function is used to navigate to a particular URL which is mentioned in config.properties file
	 * 
	 *
	 */

	public void navigateToUrl() {
		try {
			String url = rf.getProperty("URL");
			driver.get(url);
			String actualUrl = driver.getCurrentUrl();
			log.debug("Opening Salesforce Sandbox");
			compareStrings(url, actualUrl, "URL");
		} catch (Exception e) {
			report.log(LogStatus.FAIL, "Unable to navigate to the specified URL\n" + ExceptionUtils.getStackTrace(e));
			fail("Unable to navigate to the specified URL\n" + ExceptionUtils.getStackTrace(e));
		}
	}

	/**
	 * Function is used to perform type action on element
	 *
	 * @param locator  -- Get it from the object repository
	 * @param eleName  -- Name of the element which will be clicked
	 * @param testData -- Test Data to be filled
	 * @throws IOException
	 * 
	 */
	public void type(By locator, String testData, String eleName) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			driver.findElement(locator).sendKeys(testData);
			log.debug("User is able to enter " + testData + " in " + eleName + " field");
			report.log(LogStatus.PASS, "User is able to enter " + testData + " in " + eleName + " field");
		} catch (Exception e) {
			report.log(LogStatus.FAIL, report.addScreenCapture(screenshot()) + "Unable to enter " + testData + " in "
					+ eleName + " field\n" + ExceptionUtils.getStackTrace(e));
			fail("Unable to enter " + testData + " in "
					+ eleName + " field\n" + ExceptionUtils.getStackTrace(e));
		}

	}

	/**
	 * Function is used to type the password 
	 * @param locator  -- Get it from the object repository
	 * @param eleName  -- Name of the element which will be clicked
	 * @param testData -- Password encoded in Base64 
	 */

	public void typePassword(By locator, String testData, String eleName) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			driver.findElement(locator).sendKeys(testData);
			log.debug("User is able to enter password in " + eleName + " field");
			report.log(LogStatus.PASS, "User is able to enter password in " + eleName + " field");
		} catch (Exception e) {
			report.log(LogStatus.FAIL, report.addScreenCapture(screenshot()) + "Unable to enter password in " + eleName
					+ " field\n" + ExceptionUtils.getStackTrace(e));
			fail("Unable to enter password in " + eleName
					+ " field\n" + ExceptionUtils.getStackTrace(e));
		}

	}

	/**
	 * Function is used to perform click action on element
	 *
	 * @param locator-- Get it from the object repository
	 * @param eleName-- name of the element which will be clicked
	 * 
	 */

	public void click(By locator, String eleName) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			driver.findElement(locator).click();
			log.debug("User is able to click " + eleName);
			report.log(LogStatus.PASS, "User is able to click on " + eleName);
		} catch (Exception e) {
			report.log(LogStatus.FAIL, report.addScreenCapture(screenshot()) + "User is unable to click on " + eleName
					+ "\n" + ExceptionUtils.getStackTrace(e));
			fail("User is unable to click on " + eleName+ "\n" + ExceptionUtils.getStackTrace(e));
		}
	}

	/**
	 * Function is used to verify a particular element is present on the web page or not
	 * @param locator-- Get it from the object repository
	 * @param eleName-- name of the element which will be checked
	 * @return 'Yes' if element present and 'No' if element not present
	 */

	public String verifyElementIsPresent(By locator, String eleName) {
		List<WebElement> l = driver.findElements(locator);
		if (l.size() == 0) {
			log.debug(eleName + " is not present");
			return "No";
		} else {
			log.debug(eleName + " is present");
			return "Yes";
		}
	}

	/**
	 * Function is used to get a particular attribute value from the DOM
	 * @param locator-- Get it from the object repository
	 * @param attribute-- attribute name
	 * @return the attribute value
	 */

	public String getAttributeValue(By locator, String attribute) {
		try {
			String attValue = driver.findElement(locator).getAttribute(attribute);
			return attValue;
		} catch (Exception e) {
			report.log(LogStatus.FAIL,
					report.addScreenCapture(screenshot()) + "User is unable to get the attribute value of" + attribute
							+ "\n" + ExceptionUtils.getStackTrace(e));
			fail("User is unable to get the attribute value of" + attribute
					+ "\n" + ExceptionUtils.getStackTrace(e));
			return "unable to get the attribute value";
		}
	}

	/**
	 * Function is used to get a text value
	 * @param locator-- Get it from the object repository
	 * @param eleName-- name of the element 
	 * @return the extracted value
	 * @throws Exception
	 */

	public String getTextValue(By locator, String eleName) {
		try {
			String txtValue = driver.findElement(locator).getText();
			return txtValue;
		} catch (Exception e) {
			report.log(LogStatus.FAIL, report.addScreenCapture(screenshot()) + "User is unable to get the text value of"
					+ eleName + "\n" + ExceptionUtils.getStackTrace(e));
			fail("User is unable to get the text value of"
					+ eleName + "\n" + ExceptionUtils.getStackTrace(e));
			return "Unable to get text value";

		}
	}

	/**
	 * Function is used to clear the text box
	 * @param locator-- Get it from the object repository
	 * @param eleName-- name of the element 
	 * @throws Exception
	 */

	public void clearTxtBox(By locator, String eleName) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			click(locator, eleName);
			driver.findElement(locator).clear();
//			actions.keyDown(Keys.CONTROL);
//			actions.sendKeys("c");
//			actions.keyUp(Keys.CONTROL);
//			actions.sendKeys(Keys.DELETE);
//			actions.build().perform();
			log.debug("Clearing the " + eleName + " text box");
		} catch (Exception e) {
			report.log(LogStatus.FAIL, report.addScreenCapture(screenshot()) + "User is unable to clear the " + eleName
					+ " text box\n" + ExceptionUtils.getStackTrace(e));
			fail("User is unable to clear the " + eleName
					+ " text box\n" + ExceptionUtils.getStackTrace(e));
		}
	}

	/**
	 * Function is used to verify a particular message is available on the web page or not
	 * @param locator-- Get it from the object repository
	 * @param eleName-- name of the element 
	 * @throws Exception
	 */

	public void verifyMessage(By locator, String eleName) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			log.debug(eleName + " is displayed");
			report.log(LogStatus.PASS, eleName + " is displayed");
		} catch (Exception e) {
			report.log(LogStatus.FAIL, report.addScreenCapture(screenshot()) + eleName + " is not displayed\n"
					+ ExceptionUtils.getStackTrace(e));
			fail(eleName + " is not displayed\n"
					+ ExceptionUtils.getStackTrace(e));
		}
	}

	/**
	 * Function is used to compare two strings
	 * @param expectedString
	 * @param actualString
	 * @param eleName
	 * @throws IOException
	 */

	public void compareStrings(String expectedString, String actualString, String eleName) {
		if (expectedString.equals(actualString)) {
			log.debug("Actual value of " + eleName + " is same as expected value: " + actualString);
			report.log(LogStatus.PASS, "Actual value of " + eleName + " is same as expected value: " + actualString);
		} else {
			report.log(LogStatus.FAIL, report.addScreenCapture(screenshot()) + "Actual value of " + eleName
					+ " is not same as expected value: " + actualString);
		fail("Actual value of " + eleName
				+ " is not same as expected value: " + actualString);}
	}

	/**
	 * Function is used to take Screenshot
	 *
	 * @return -- the taken screenshot
	 * @throws IOException
	 */

	public String screenshot() {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File Dest = new File("src/../ErrImages/" + ".png");
			FileUtils.copyFile(scrFile, Dest);
			String errflpath = Dest.getAbsolutePath();
			return errflpath;
		} catch (Exception e) {
			report.log(LogStatus.FAIL, "Unable to take screenshot\n" + ExceptionUtils.getStackTrace(e));
			fail("Unable to take screenshot\n" + ExceptionUtils.getStackTrace(e));
			return "Unable to take screenshot";
		}
	}
}
