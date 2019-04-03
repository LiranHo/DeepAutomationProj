package Project.Tests;

import Project.BaseTest;
import Project.Main;
import Project.TestWrapper.Device;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class Test_Simple_1 extends BaseTest {


    @DisplayName("Test_Simple_web")
    @Test
    public void Test_Simple_web() {
        Main.sout("Info!", "Starting test Test_Simple_web for device " + device.getSerialnumber());

        try {
            driver.get("https://www.google.com");
        } catch (Exception e) {

        }

        if(client.isElementFound("native", "xpath=//*[@text='No Thanks']")) {
            try {
                driver.findElement(By.xpath("//*[@text='No Thanks']")).click();
            } catch (Exception e) {
            }
        }

        WebElement searchBar;
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
            searchBar= driver.findElement(By.name("q"));
        } catch (Exception e) {
            client.report(e.getMessage(),false);
            driver.quit();
            Main.sout("Exception | Test_Simple_web ",device.getSerialnumber(),e.getMessage());

            throw e;
        }
        searchBar.sendKeys("Experitest");
        driver.findElement(By.xpath("//*[@css='BUTTON.Tg7LZd']")).click();
    }

}
