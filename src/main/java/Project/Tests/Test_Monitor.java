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

public class Test_Monitor extends BaseTest {




    @DisplayName("Test_Monitor")
    @Test
    public void Test_Monitor() throws InterruptedException {
        Main.sout("Info!", "Starting test Test_Monitor for device " + device.getSerialnumber());
        client.addTestProperty("TestType", "TestMonitor");
        client.startMonitor("com.experitest.ExperiBank");
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.findElement(By.xpath("//*[@id='usernameTextField']")).sendKeys("company");
        driver.hideKeyboard();
        driver.findElement(By.xpath("//*[@id='passwordTextField']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@id='loginButton']")).click();
        driver.findElement(By.xpath("//*[@id='makePaymentButton']")).click();
        driver.findElement(By.xpath("//*[@id='phoneTextField']")).sendKeys("0541234567");
        driver.findElement(By.xpath("//*[@id='nameTextField']")).sendKeys("Jon Snow");
        driver.findElement(By.xpath("//*[@id='amountTextField']")).sendKeys("50");
        driver.findElement(By.xpath("//*[@id='countryButton']")).click();
        driver.findElement(By.linkText("Switzerland")).click();
        driver.findElement(By.xpath("//*[@id='sendPaymentButton']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@text='Yes']")).click();
        client.report("before get monitor data", true);

        String sub_sessionID = sessionID.split(":")[1];
        try {
            client.getMonitorsData(Main.innerDirectoryPath+"\\getMonitor\\"+device.getSerialnumber()+"\\"+sub_sessionID+".csv");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        client.report("after get monitor data", true);



    }


    @Override
    public void ChooseAppDC(){
        dc.setCapability("testName", "Test Monitor");
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
