package whoopdicity.examples;

import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Example to demonstrate generic before/after hooks
 */
public class ExampleHooks {

    @Before
    public void execute_before_scenario() {
        System.out.println("Before");
    }

    @After
    public void execute_after_scenario() {
        System.out.println("After");
    }
}
