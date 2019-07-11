package Project.Tests;

import Project.BaseTest;
import Project.Main;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;

import static io.appium.java_client.remote.MobileCapabilityType.APP;

public class Test_Instrument extends BaseTest {




    @DisplayName("Test_Instrument")
    @Test
    public void Test_Simple_EriBank() throws InterruptedException {
        Main.sout("Info!", "Starting test Test_Instrument for device " + device.getSerialnumber());
        String prefix = "id"; // for Android
        if (device.isIOS()) {
            prefix = "accessibilityLabel";
        }

        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.findElement(By.xpath("//*[@" + prefix + "='usernameTextField']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@" + prefix + "='passwordTextField']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@" + prefix + "='loginButton']")).click();
        driver.findElement(By.xpath("//*[@" + prefix + "='makePaymentButton']")).click();
        driver.findElement(By.xpath("//*[@" + prefix + "='phoneTextField']")).sendKeys("0541234567");
        driver.findElement(By.xpath("//*[@" + prefix + "='nameTextField']")).sendKeys("Jon Snow");
        driver.findElement(By.xpath("//*[@" + prefix + "='amountTextField']")).sendKeys("50");
        driver.findElement(By.xpath("//*[@" + prefix + "='countryButton']")).click();
        String deviceVersion = device.getVersion();
        if (!deviceVersion.startsWith("13.")) {
            driver.findElement(By.xpath("//*[@text='Switzerland']")).click();
            driver.findElement(By.xpath("//*[@" + prefix + "='sendPaymentButton']")).click();
            driver.findElement(By.xpath("//*[@text='Yes']")).click();
        }

    }


    @Override
    public void ChooseAppDC(){
        dc.setCapability("testName", "Test Eribank Instrument");
        dc.setCapability("instrumentApp", true);
        if (device.isAndroid()) {
            dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
            dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
            dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");

        } else { //Device IOS
            dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
            dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
        }
    }

}
