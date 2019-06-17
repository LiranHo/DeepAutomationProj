package Project.Tests;

import Project.BaseTest;
import Project.Main;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Test_Debug_App extends BaseTest {


    @DisplayName("Install_debug_application")
    @Test
    public void LoggingDevices_test() {
        Main.sout("Info!", "Starting debug_app_test for device " + device.getSerialnumber()+".txt");

        if (device.getSerialnumber().equals("016fd8f1dd4f22bd")) {
            client.sleep(5000);
        }

    }


    @Override
    public void ChooseAppDC(){
        dc.setCapability("testName", "Install debug_app application");
        if (device.getSerialnumber().equals("016fd8f1dd4f22bd")) {
            if (device.isAndroid()) {
                dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.mobile.myapplication/.ScrollingActivity");
                dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.mobile.myapplication");
                dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".ScrollingActivity");
            }
        }


    }

}
