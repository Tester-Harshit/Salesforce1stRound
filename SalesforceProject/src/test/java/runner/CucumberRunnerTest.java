package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/java/Features/CaseManagement.feature"}
        ,glue={"stepDefinitions", "com.salesforce.hooks"}
        ,tags = "@Salesforce"
        ,monochrome=true
        ,plugin = {"pretty","json:target/cucumber-report/cucumber.json"}
)

public class CucumberRunnerTest {

}