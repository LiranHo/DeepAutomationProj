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

public class SimpleWebTest extends BaseTest {


    @DisplayName("SimpleWebTest")
    @Test
    public void SimpleWebTest() {
        Main.sout("Info!", "Starting test Test_Simple_web for device " + device.getSerialnumber());

        if (device.isAndroid()) {
            try { // set wifi to 'lab' - not in Xioami devices!
                String manuf = device.getManufactor();
                System.out.println("***** manufactor for device " + device.getManufactor() + " is: " + manuf);
                if (!manuf.equals("xiaomi")) {
//                    client.run("am instrument -w -r com.experitest.device.devicecontrol/com.experitest.device.devicecontrol.instrumentations.WifiForgetAll"); // forget all wifi
                    client.run("am instrument -w -r -e ssid lab -e password 0528544681 com.experitest.device.devicecontrol/com.experitest.device.devicecontrol.instrumentations.WifiConfig");
                }
            } catch (Exception e) {

            }
            String appsInstalled = client.getInstalledApplications();
            if (!appsInstalled.contains("com.android.chrome")) { // check if chrome installed, if not - install it
                System.out.printf("Chrome is not installed, install it now");
                driver.executeScript("seetest:client.install(\"cloud:com.android.chrome/com.google.android.apps.chrome.Main\", \"true\", \"true\")");
            }
        }

        try {
            client.sleep(15000);
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


    public void ChooseAppDC(){
        dc.setCapability("testName","SimpleWebTest");
    }


}
