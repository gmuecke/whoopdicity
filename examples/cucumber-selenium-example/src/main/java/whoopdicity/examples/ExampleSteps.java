package whoopdicity.examples;

import org.openqa.selenium.By;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ExampleSteps {

    private final BrowserHooks browser;

    private String baseUrl;

    public ExampleSteps(final BrowserHooks browser) {
        this.browser = browser;
    }

    @Before
    public void setUp() throws Exception {

        this.baseUrl = "http://www.google.com/";

    }

    @Angenommen("^es gibt (\\d+) Kurse die nicht das Theman \"([^\"]*)\" haben$")
    @Given("^there are (\\d+) courses which do not have the topic \"([^\"]*)\"$")
    public void there_are_courses_which_do_not_have_the_topic(final int arg1, final String arg2) throws Throwable {
        this.browser.getDriver().get(this.baseUrl);
    }

    @Angenommen("^es gibt (\\d+) Kurse, A(\\d+) und B(\\d+), die jeweils \"([^\"]*)\" zum Thema haben$")
    @Given("^there are (\\d+) courses, A(\\d+) and B(\\d+), that each have \"([^\"]*)\" as one of the topics$")
    public void there_are_courses_A_and_B_that_each_have_as_one_of_the_topics(final int arg1, final int arg2,
            final int arg3, final String arg4) throws Throwable {
        System.out.printf("Prepare courses %s, %s, topic: %s\n", arg2, arg3, arg4);
    }

    @Wenn("^ich nach \"([^\"]*)\" suche$")
    @When("^I search for \"([^\"]*)\"$")
    public void I_search_for(final String arg1) throws Throwable {
        this.browser.getDriver().findElement(By.id("gbqfq")).sendKeys(arg1);
    }

    @Dann("^sehe ich die Folgenden Kurse$")
    @Then("^I should see the following courses:$")
    public void I_should_see_the_following_courses(final DataTable arg1) throws Throwable {
        System.out.println("use some clever selenium logic to search the site");
    }
}
