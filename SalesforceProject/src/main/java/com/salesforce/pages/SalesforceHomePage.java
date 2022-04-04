package com.salesforce.pages;

import org.openqa.selenium.By;

import com.salesforce.webcommands.WebActions;

public class SalesforceHomePage extends WebActions{

	By applicationName = By.xpath("//*[@title = 'Service Cloud']");
	
	By appLauncher = By.xpath("//div[starts-with(@class,'appLauncher')]/button/div");
	
	By searchObject = By.xpath("//*[@role='combobox']/descendant::input[@type='search']");
	
	String xpathForObjName = "//*[@role='combobox']/descendant::b[contains(text(),'";
	
	
	public void clickAppLauncher() throws InterruptedException {
		Thread.sleep(3000);
		click(appLauncher, "App Launcher");
	}
	
	public void navigateToApplication(String appName)  {
		type(searchObject, appName, "Search");
		By toSearch = By.xpath(xpathForObjName+appName+"')]");
		click(toSearch, appName);
	}
	
	public void navigateToObject(String objName) {
		By toSearch = By.xpath(xpathForObjName+objName+"')]");
		click(toSearch, objName);
	}
}
