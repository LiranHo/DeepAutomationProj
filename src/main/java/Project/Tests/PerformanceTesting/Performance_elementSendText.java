package Project.Tests.PerformanceTesting;


import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;

import javax.lang.model.element.Element;

public class Performance_elementSendText extends PerformanceTesting_BaseTest {
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
    public void Performance_elementSendText() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        long start = System.currentTimeMillis();
        driver.findElement(By.xpath("//*[@id='usernameTextField']")).sendKeys("performanceTest");
        long end = System.currentTimeMillis();
        driver.hideKeyboard();

        long time=0;

        if(driver.findElement(By.xpath("//*[@id='usernameTextField']")).getAttribute("text").equalsIgnoreCase("performanceTest")) {
            time =  calculate_commnand_time("Send Keys",start, end);
       }
        driver.closeApp();
        System.out.println(device.getSerialnumber()+"| The command time is: "+time);

        System.out.println("Report URL: "+ driver.getCapabilities().getCapability("reportUrl"));

    }


}
