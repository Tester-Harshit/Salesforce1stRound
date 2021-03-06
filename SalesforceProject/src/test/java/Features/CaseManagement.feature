@Salesforce
Feature: Case Management for Service Agents
Description: The purpose of this feature is to test the case creation.
Background: User is Logged In
		Given user is on login page
    When user enter the username and password
    And clicks on the login button
    And User should be navigated to the Home page
   # When User clicks on App launcher button
   # And User search the "Service Cloud" application and should be navigated to it
   # When User clicks on App launcher button
   # And User search the "Cases" object and should be navigated to it
    
@SmokeTest1
  Scenario Outline: Service Agent should successfully create a case.
  	 Given a new Case with Origin equal to "<CaseOrigin>"
	   And Subject equal to "<Subject>"
		 When the Service Agent clicks on Save
		 Then the Case Details page should be displayed and Origin should be equal to "<CaseOrigin>"
		 
		 Examples:
 			|CaseOrigin|Subject|
 			|Phone		 |Password Request|
 			
@SmokeTest2
 Scenario Outline: A default Subject should always be assigned to the Case.
		 Given a Case with Origin equal to "<CaseOrigin>"
		 And without Subject
		 When the Service Agent clicks on Save
		 Then the Case Details page should be displayed and Subject should not be blank
		 Examples:
 			|CaseOrigin|
 			|Phone		 | 
 			
@SmokeTest3
 Scenario Outline: Service Agent Should not Delete Cases.
		 Given a Case "<CaseNumner>"
		 When the Service Agent clicks on Delete
		 Then an Error Message saying "Case Cannot Be Deleted!" should be displayed
		 Examples:
 			|CaseNumner|
 			|00001048|