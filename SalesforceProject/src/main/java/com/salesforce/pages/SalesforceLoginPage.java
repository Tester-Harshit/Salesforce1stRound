package com.salesforce.pages;

import org.openqa.selenium.By;
import com.relevantcodes.extentreports.LogStatus;
import com.salesforce.utils.DecodingStrings;
import com.salesforce.webcommands.WebActions;

public class SalesforceLoginPage extends WebActions {

	DecodingStrings ds = new DecodingStrings();

	By username = By.id("username");

	By password = By.id("password");

	By login = By.id("Login");

	By error = By.id("error");

	public void urlNavigation() {
		navigateToUrl();
	}

	public void setUserNamePassword() {
		type(username, rf.getProperty("Username"), "Username");
		typePassword(password, ds.decoder(rf.getProperty("Password")), "Password");
	}

	public void clickLogin() {
		click(login, "Login");

	}

	public void verifyLogin() {
		String s = verifyElementIsPresent(error, "Login Error message");
		if (s.equalsIgnoreCase("Yes")) {
			report.log(LogStatus.FAIL,
					report.addScreenCapture(screenshot()) + "Unable to Login Please verify the error message");
		} else
			report.log(LogStatus.PASS, "Login Successfull");
	}
}
