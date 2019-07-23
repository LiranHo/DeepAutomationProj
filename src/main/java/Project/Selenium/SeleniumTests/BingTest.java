package Project.Selenium.SeleniumTests;

import Project.Selenium.BaseTest_Browser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class BingTest extends BaseTest_Browser {

    String browserType;

    public void setBrowserType(String browserType){
        this.browserType= browserType;
    }

    @Test
    @DisplayName("Selenium_BingTest")
    public void Selenium_BingTest() {
        int i = 0;
        System.out.println("Start BingTest for "+browser.getSerialnumber());
        //while (i < 30 * 3) {//6 mini
        while (i < 10 * 3) {//6 mini
            driver.get("https://www.bing.com/search?q=" + i);
            sleep();
            i++;
        }

    }

    private void sleep() {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCustomeCapabilities(){
//        System.out.println("Browser type is: " + browserType + "| " + browser.getSerialnumber());
//        testName = this.getClass().getSimpleName() + " " + browserType;
//        dc.setCapability("testName",testName);
//        dc.setCapability(CapabilityType.BROWSER_NAME, browserType);

    }



}
