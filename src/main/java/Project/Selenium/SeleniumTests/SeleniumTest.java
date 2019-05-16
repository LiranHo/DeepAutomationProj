package Project.Selenium.SeleniumTests;

import Project.Selenium.BaseTest_Browser;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
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

public class SeleniumTest extends BaseTest_Browser {

    String browserType = browser.getplatform();

    @DisplayName("SeleniumTest")
    @Test
    public void browserTestGoogleSearch() {
        for(int i=0;i<2;i++){
            driver.get("https://www.google.com");
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
            WebElement searchBar = driver.findElement(By.name("q"));
            searchBar.click();
            searchBar.sendKeys("Experitest");
            searchBar.sendKeys(Keys.ENTER);
        }
    }

    @Override
    public void ChooseAppDC(){
        testName = this.getClass().getSimpleName() + " " +browser.getplatform();
        dc.setCapability("testName",testName);

    }

}
