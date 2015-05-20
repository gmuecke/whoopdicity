package whoopdicity.examples;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * The Example test demonstrate how Behavior-Driven Tests are created with cucumber. The Story is written in the
 * referenced feature file as plain text.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = {
    "classpath:features/example"
}, glue = {
    "whoopdicity.examples"
}, tags = {
    "@BrowserExample"
})
public class ExampleBrowserCucumberTest {

}
