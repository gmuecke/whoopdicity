package whoopdicity.examples;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Browser hook to select a Selenium web driver
 */
public final class BrowserHooks {

    private enum DriverType {
        headless,
        firefox,
        ie,
        chrome, ;
    }

    private WebDriver driver;

    /**
     * The chosen driver type. The type can be specified by a specific tag.
     */
    private DriverType driverType = DriverType.headless;

    /**
     * Returns the WebDriver that was enabled with a specific tag.
     * 
     * @return
     */
    public WebDriver getDriver() {
        if (this.driver == null) {
            switch (this.driverType) {
                case ie:
                    this.driver = new InternetExplorerDriver();
                    break;
                case firefox:
                    this.driver = new FirefoxDriver();
                    break;
                case chrome:
                    this.driver = new ChromeDriver();
                    break;
                case headless:
                    final HtmlUnitDriver headlessDriver;
                    final BrowserVersion version = BrowserVersion.FIREFOX_17;
                    headlessDriver = new HtmlUnitDriver(version);
                    headlessDriver.setJavascriptEnabled(true);
                    this.driver = headlessDriver;
                    break;
                default:
                    this.driver = new FirefoxDriver();
                    break;
            }
            this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }

        return this.driver;
    }

    @Before("@WithFirefox")
    public void setupScenario_Firefox() {
        this.driverType = DriverType.firefox;
    }

    @Before("@WithIE")
    public void setupScenario_InternetExplorer() {
        this.driverType = DriverType.ie;
    }

    @Before("@WithChrome")
    public void setupScenario_Chrome() {
        this.driverType = DriverType.chrome;
    }

    @Before("@WithoutBrowser")
    public void setupScenario_Headless() {
        this.driverType = DriverType.headless;
    }

    @After()
    public void tearDownScenario() {
        if (this.driver != null) {
            this.driver.quit();
        }
        this.driver = null;
    }
}
