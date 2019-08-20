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

public class Test_Install_From_URL extends BaseTest {


    @DisplayName("Test_Install_From_URL")
    @Test
    public   void Test_Install_From_URL() throws InterruptedException {
        Main.sout("Info!", "Starting test Test_Install_From_URL for device " + device.getSerialnumber());
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.findElement(By.xpath("//*[@id='usernameTextField']")).sendKeys("company");
        driver.hideKeyboard();
        driver.findElement(By.xpath("//*[@id='passwordTextField']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@id='loginButton']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='makePaymentButton']")).click();
        driver.findElement(By.xpath("//*[@id='phoneTextField']")).sendKeys("0541234567");
        driver.findElement(By.xpath("//*[@id='nameTextField']")).sendKeys("Jon Snow");
        driver.findElement(By.xpath("//*[@id='amountTextField']")).sendKeys("50");
        driver.findElement(By.xpath("//*[@id='countryButton']")).click();
        Thread.sleep(2000);
        String deviceVersion = device.getVersion();
        if (!deviceVersion.startsWith("13.")) {
            driver.findElement(By.xpath("//*[@text='Switzerland']")).click();
            driver.findElement(By.xpath("//*[@id='sendPaymentButton']")).click();
            driver.findElement(By.xpath("//*[@text='Yes']")).click();
        }


    }


    @Override
    public void ChooseAppDC(){
        dc.setCapability("testName", "Test Install From URL");
        if (device.isAndroid()) {
            dc.setCapability(MobileCapabilityType.APP, "http://192.168.1.213:8090/guestAuth/repository/download/AndroidUtilities_AndroidAppsMaster/.lastSuccessful/eribank.apk");
            dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
            dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");

        } else { //Device IOS
            dc.setCapability(MobileCapabilityType.APP, "http://192.168.1.213:8090/guestAuth/repository/download/IOSFrameworkSatelliteApps_IosPublishingMaster/.lastSuccessful/Eribank.ipa");
            dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
        }
    }

}
