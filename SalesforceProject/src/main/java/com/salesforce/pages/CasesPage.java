package com.salesforce.pages;

import org.openqa.selenium.By;

import com.relevantcodes.extentreports.LogStatus;
import com.salesforce.webcommands.WebActions;

public class CasesPage extends WebActions {

	By caseBtnXpath, globalSearchResultXpath, fieldXpath, listViewRecord;

	By casesTab = By.xpath("//div[starts-with(@class, 'oneConsoleNav')]/descendant::span[text() = 'Cases']");

	By newCaseBtn = By.xpath("//*[@title = 'New']");

	By caseOriginDropDwon = By.xpath("//*[text() = 'New Case']/ancestor::div[@class='actionBody']//span[text() = 'Case Origin']/../following-sibling::div//a");

	String caseOriginDropDownValueXpath = "//a[text() = '";

	By subjectTxtBox = By.xpath("//*[text() = 'New Case']/ancestor::div[@class='actionBody']//span[text() = 'Subject']/../following-sibling::input");

	By caseSaveBtn = By.xpath("//div[@class='actionsContainer']//span[text() = 'Save']");

	By selectedCase = By.xpath("(//a[starts-with(@class,'tabHeader ') and @tabindex=0 and @role = 'tab'])[1]");

	By caseCreatedSuccessMessage = By.xpath("//div[@role='alert' and @data-key='success']");

//	String caseEditButton = "//*[text() = '00001026']/ancestor::div[starts-with(@class,'highlights')]/descendant::button[@name='Edit']";

	By caseSubjectEditTxtBox = By.xpath("//div[@class='actionBody']//span[text() = 'Subject']/../following-sibling::input");

	By caseListViewDrpDwn = By.xpath("//button[@title='Select a List View']");

	By caseListView = By.xpath("//span[@class=' virtualAutocompleteOptionText' and text()='Case with Phone as Origin']");

	By caseDeletePopUpBtn = By.xpath("//span[text() = 'Delete']");

	By cannotDelMsg = By.xpath("//*[contains(text(), 'Case Cannot Be Deleted!')]");
	
	By globalSearch = By.xpath("//*[@title = 'Search Cases and more']");
	
	
	

	public void newCaseWithOrigin(String caseOrigin) {
		click(casesTab, "Cases Tab");
		click(newCaseBtn, "New Case");
		click(caseOriginDropDwon, "Case Origin");
		By caseOriginDropDownValue = By.xpath(caseOriginDropDownValueXpath + caseOrigin + "']");
		click(caseOriginDropDownValue, caseOrigin);
	}

	public void setCaseSubject(String caseSubject) {
		type(subjectTxtBox, caseSubject, "Subject");
	}

	public void saveCase() {
		click(caseSaveBtn, "Save Button");
	}

	public void verifyCaseCreated(String caseOriginExpected) {
		verifyMessage(caseCreatedSuccessMessage, "Success Message");
		String caseNumber = getAttributeValue(selectedCase, "title");
		createFieldsXpathDetailsPage(caseNumber, "Case Origin");
		String caseOriginActual = getTextValue(fieldXpath, "Case Origin");
		compareStrings(caseOriginExpected, caseOriginActual, "Case Origin");
	}

	public void openCaseWithOrigin(String caseOrigin) throws InterruptedException {
		click(casesTab, "Cases Tab");
		click(caseListViewDrpDwn, "Case List view Drop Down");
		click(caseListView, "Case with origin List View");
		Thread.sleep(2000);
		createRecordXpathInListView(caseOrigin);
		click(listViewRecord, "Case with Origin as Phone");
	}

	public void editSubject() {
		String caseNumber = getAttributeValue(selectedCase, "title");
		createCaseButtonXpath(caseNumber, "Edit");
		click(caseBtnXpath, "Edit Button");
		clearTxtBox(caseSubjectEditTxtBox, "Case Subject");
	}

	public void verifyCaseCreratedWithoutSubject() {
		verifyMessage(caseCreatedSuccessMessage, "Success Message");
		String caseNumber = getAttributeValue(selectedCase, "title");
		createFieldsXpathDetailsPage(caseNumber, "Subject");
		String subjectActual = getTextValue(fieldXpath, "Subject");
		if (subjectActual != "")
			report.log(LogStatus.PASS, " Subject is not blank");
		else
			report.log(LogStatus.FAIL, " Subject is blank");
	}

	public void openCase(String caseNumber) {
		click(casesTab, "Cases Tab");
		type(globalSearch, caseNumber, "Search the Case "+caseNumber);
		createXpathForGlobalSearchResult(caseNumber);
		click(globalSearchResultXpath, "Case Number");
		createCaseButtonXpath(caseNumber, "Delete");
		click(caseBtnXpath, "Delete");
	}

	public void caseDelete() {
		click(caseDeletePopUpBtn, "Confirm Delete");
	}

	public void verifyDelErrMsg(String expectedErrorMsg) {
		verifyMessage(cannotDelMsg, expectedErrorMsg);
	}

	/***************************************************
	 * ************************************************* 
	 * Below are the methods for creating dynamic Xpath* 
	 * *************************************************
	 ***************************************************/

	public void createCaseButtonXpath(String caseNumber, String btnName) {

		caseBtnXpath = By.xpath("//*[text() = '" + caseNumber
				+ "']/ancestor::div[starts-with(@class,'highlights')]/descendant::button[@name='" + btnName + "']");
	}

	public void createXpathForGlobalSearchResult(String caseNumber) {

		globalSearchResultXpath = By.xpath("//*[@title= '"+caseNumber+"']/ancestor::a[@role = 'option']");
	}

	public void createFieldsXpathDetailsPage(String caseNumber, String fieldName) {

		fieldXpath = By.xpath("//*[text() = '" + caseNumber
				+ "' and @slot='outputField']/ancestor::div[starts-with(@class,'record-body')]//span[text() = '"
				+ fieldName + "'] /../../descendant::lightning-formatted-text");
	}

	public void createRecordXpathInListView(String text) {

		listViewRecord = By.xpath("(//*[text() = '" + text + "']/ancestor::td/preceding-sibling::th)[1]/descendant::a");
	}
}
