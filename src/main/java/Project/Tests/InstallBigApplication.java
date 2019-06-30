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

public class InstallBigApplication extends BaseTest {


    @DisplayName("Install_big_application")
//    @Test
    public void LoggingDevices_test() {
        Main.sout("Info!", "Starting Test_InstallBigApp for device " + device.getSerialnumber()+".txt");

        if (!(device.getSerialnumber().equals("016fd8f1dd4f22bd"))) {
            client.startLoggingDevice(Main.innerDirectoryPath+"\\"+device.getSerialnumber()+"\\"+testStartTime);
            client.sleep(1000*30);

            client.closeAllApplications();

            try {
                client.stopLoggingDevice();
            } catch (Exception e) {
                client.report(e.getMessage(),false);
                Main.sout("Exception | InstallBigApplication ",device.getSerialnumber(),e.getMessage());
                driver.quit();
                throw e;
            }
        }


    }


    @Override
    public void ChooseAppDC(){
        dc.setCapability("testName", "Install big application");
        if (device.isAndroid()) {
            if (!(device.getSerialnumber().equals("016fd8f1dd4f22bd"))) {
                dc.setCapability(MobileCapabilityType.APP, "cloud:com.example.shaharyannay.dotgame/.Activity.LoginActivity");
                dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.example.shaharyannay.dotgame");
                dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".Activity.LoginActivity");
            }
        } else { //Device IOS
            dc.setCapability(MobileCapabilityType.APP, "cloud:com.nianticlabs.pokemongo");
            dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.nianticlabs.pokemongo");
        }

    }

}
