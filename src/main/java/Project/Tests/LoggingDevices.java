package Project.Tests;

import Project.BaseTest;
import Project.Main;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoggingDevices extends BaseTest {


    @DisplayName("LoggingDevices_test")
    @Test
    public void LoggingDevices_test() {
        Main.sout("Info!", "Starting testLoggingDevices_test for device " + device.getSerialnumber()+".txt");

            client.startLoggingDevice(Main.innerDirectoryPath+"\\"+device.getSerialnumber()+"\\"+testStartTime);
            client.sleep(1000*30);

        try {
            client.stopLoggingDevice();
        } catch (Exception e) {
            client.report(e.getMessage(),false);
            Main.sout("Exception | LoggingDevices ",device.getSerialnumber(),e.getMessage());
            driver.quit();
            throw e;
        }
    }


    @Override
    public void ChooseAppDC(){
        dc.setCapability("testName", "LoggingDevices_test");

    }

}
