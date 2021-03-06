//package Project.Tests;
//
//import Project.BaseTest;
//import Project.Main;
//import io.appium.java_client.remote.AndroidMobileCapabilityType;
//import io.appium.java_client.remote.IOSMobileCapabilityType;
//import io.appium.java_client.remote.MobileCapabilityType;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.ScreenOrientation;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.text.SimpleDateFormat;
//
//import static io.appium.java_client.remote.MobileCapabilityType.APP;
//
//public class Test_OSS extends BaseTest {
//
//
//    @DisplayName("Test_OSS")
//    @Test
//    public void Test_OSS() throws InterruptedException {
//        String prefix = "id"; // for Android
//        if (device.isIOS()) {
//            prefix = "label";
//        }
//        if (device.isIOS()) {
//            Main.sout("Info!", "Starting test Test_OSS for device " + device.getSerialnumber());
//            driver.rotate(ScreenOrientation.PORTRAIT);
//            driver.findElement(By.xpath("//*[@" + prefix + "='usernameTextField']")).sendKeys("company");
//            driver.findElement(By.xpath("//*[@" + prefix + "='passwordTextField']")).sendKeys("company");
//            driver.findElement(By.xpath("//*[@" + prefix + "='loginButton']")).click();
//            driver.findElement(By.xpath("//*[@" + prefix + "='makePaymentButton']")).click();
//            driver.findElement(By.xpath("//*[@" + prefix + "='phoneTextField']")).sendKeys("0541234567");
//            driver.findElement(By.xpath("//*[@" + prefix + "='nameTextField']")).sendKeys("Jon Snow");
//            driver.findElement(By.xpath("//*[@" + prefix + "='amountTextField']")).sendKeys("50");
//            driver.findElement(By.xpath("//*[@" + prefix + "='countryButton']")).click();
//            driver.findElement(By.xpath("//*[@" + prefix + "='Switzerland']")).click();
//            driver.findElement(By.xpath("//*[@" + prefix + "='sendPaymentButton']")).click();
//            driver.findElement(By.xpath("//*[@" + prefix + "='Yes']")).click();
//        }
//    }
//
//
//    @Override
//    public void ChooseAppDC(){
//        dc.setCapability("testName", "Test general");
//        if (device.isAndroid()) {
//            dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
//            dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
//            dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
//
//        } else { //Device IOS
//            dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank:3053");
//            dc.setCapability("bundleId", "com.experitest.ExperiBank");
//            if (device.getAgentName().equals("Agent 2 - 2.105")) {
//                dc.setCapability("testName", "Test OSS");
//                dc.setCapability("appiumVersion", "1.12.1-p0");
//                dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.2.6");
//                dc.setCapability("automationName", "XCUITest");
//                dc.setCapability("deviceName", "auto");
//                dc.setCapability("udid", device.getSerialnumber());
//            }
//
//        }
//    }
//
//}
