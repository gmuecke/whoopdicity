/**
 * 
 */
package whoopdicity.examples;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Gerald
 */
public class LoginSteps {

    private String username;
    private String password;

    @Given("^my username is \"([^\"]*)\"$")
    public void my_username_is(final String arg1) throws Throwable {
        this.username = arg1;
    }

    @Given("^my password is \"([^\"]*)\"$")
    public void my_password_is(final String arg1) throws Throwable {
        this.password = arg1;
    }

    @When("^I enter my credentials$")
    public void I_enter_my_credentials() throws Throwable {
        System.out.printf("Enter: %s:%s \n", this.username, this.password);
    }

    @When("^press login$")
    public void press_login() throws Throwable {
        System.out.println("Login!");
    }

    @Then("^I am authenticated$")
    public void I_am_authenticated() throws Throwable {
        System.out.println("Authenticated!");
    }
}
