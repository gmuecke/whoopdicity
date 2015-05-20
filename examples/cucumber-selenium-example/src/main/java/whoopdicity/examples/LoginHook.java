/**
 * 
 */
package whoopdicity.examples;

import cucumber.api.java.Before;

/**
 *
 */
public class LoginHook {

    private final LoginSteps loginSteps;

    public LoginHook(final LoginSteps loginSteps) {
        this.loginSteps = loginSteps;
    }

    @Before("@asUserA01")
    public void setupUser() throws Throwable {
        this.loginSteps.my_username_is("A01");
        this.loginSteps.my_password_is("pw01");
        this.loginSteps.I_enter_my_credentials();
        this.loginSteps.press_login();
        this.loginSteps.I_am_authenticated();
    }
}
