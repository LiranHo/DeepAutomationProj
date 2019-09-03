package Project.Tests.PerformanceTesting;


import Project.Main;
import Project.PerformanceTesting.connectToDB;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class Performance_click extends PerformanceTesting_BaseTest {
    //Tests in Eribank app

    @Override
    public void ChooseAppDC(){
        dc.setCapability("testName","SimpleEriBankTest");
        if (device.isAndroid()) {
            dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
            dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
            dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");

        } else { //Device IOS
            dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
            dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
        }
    }

    @Test
    public void Performance_click() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.findElement(By.xpath("//*[@id='usernameTextField']")).sendKeys("company");
        driver.hideKeyboard();
        driver.findElement(By.xpath("//*[@id='passwordTextField']")).sendKeys("company");
        long start = System.currentTimeMillis();
        driver.findElement(By.xpath("//*[@id='loginButton']")).click();
        long end = System.currentTimeMillis();

        long time=0;
        if(!driver.findElements(By.xpath("//*[@id='makePaymentButton']")).isEmpty()) {
            time =  calculate_commnand_time(start, end);
       }
        driver.closeApp();
        System.out.println(device.getSerialnumber()+"| The command time is: "+time);

        System.out.println("Report URL: "+ driver.getCapabilities().getCapability("reportUrl"));

    }


}
