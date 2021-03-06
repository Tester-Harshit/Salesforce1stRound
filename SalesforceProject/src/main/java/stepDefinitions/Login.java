package stepDefinitions;


import com.salesforce.pages.CasesPage;
import com.salesforce.pages.SalesforceHomePage;
import com.salesforce.pages.SalesforceLoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Login {
	SalesforceLoginPage lp = new SalesforceLoginPage();
	SalesforceHomePage hp = new SalesforceHomePage();
	CasesPage cp = new CasesPage();

    @Given("user is on login page")
    public void user_is_on_login_page()   {
        lp.urlNavigation();
    }

    @When("user enter the username and password")
    public void user_enter_the_username_and_password() {
        lp.setUserNamePassword();
    }

    @And("clicks on the login button")
    public void clicks_on_the_login_button() {
    	lp.clickLogin();
    }

    @And("User should be navigated to the Home page")
    public void user_should_be_navigated_to_the_home_page()  {
        lp.verifyLogin();
    }
    @When("User clicks on App launcher button")
    public void user_clicks_on_app_launcher_button() throws InterruptedException {
    	hp.clickAppLauncher();
    }
    
    @When("User search the {string} application and should be navigated to it")
    public void user_search_the_application_and_should_be_navigated_to_it(String appName)  {
    	hp.navigateToApplication(appName);
    }

    @When("User search the {string} object and should be navigated to it")
    public void user_search_the_object_and_should_be_navigated_to_it(String sfdcObjName)  {
    	hp.navigateToObject(sfdcObjName);
    }

    @Given("a new Case with Origin equal to {string}")
    public void a_new_case_with_origin_equal_to(String caseOrigin) {
    	cp.newCaseWithOrigin(caseOrigin);
    }

    @Given("Subject equal to {string}")
    public void subject_equal_to(String caseSubject) {
        cp.setCaseSubject(caseSubject);
    }

    @When("the Service Agent clicks on Save")
    public void the_service_agent_clicks_on_save()  {
    	cp.saveCase();
       
    }

    @Then("the Case Details page should be displayed and Origin should be equal to {string}")
    public void the_case_details_page_should_be_displayed_and_origin_should_be_equal_to(String caseOrigin)  {
        cp.verifyCaseCreated(caseOrigin);
    }

    @Given("a Case with Origin equal to {string}")
    public void a_case_with_origin_equal_to(String caseOrigin) throws Exception  {
        cp.openCaseWithOrigin(caseOrigin);
    }
    @Given("without Subject")
    public void without_subject() {
        cp.editSubject();
    }
    @Then("the Case Details page should be displayed and Subject should not be blank")
    public void the_case_details_page_should_be_displayed_and_subject_should_not_be_blank() {
    	cp.verifyCaseCreratedWithoutSubject();
       
    }
    @Given("a Case {string}")
    public void a_case(String caseNumber) {
        cp.openCase(caseNumber);
    }

    @When("the Service Agent clicks on Delete")
    public void the_service_agent_clicks_on_delete() {
        cp.caseDelete();
    }

    @Then("an Error Message saying {string} should be displayed")
    public void an_error_message_saying_should_be_displayed(String errorMessage	) {
        cp.verifyDelErrMsg(errorMessage);
    }

}
