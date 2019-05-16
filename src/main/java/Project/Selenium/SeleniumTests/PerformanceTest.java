package Project.Selenium.SeleniumTests;

import Project.Selenium.BaseTest_Browser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class PerformanceTest extends BaseTest_Browser {
    String browserType = browser.getplatform();

    public void setBrowserType(String browserType){
        this.browserType= browserType;
    }


    @Test
    @DisplayName("PerformanceTest")
    public void PerformanceTest() {
        wait = new WebDriverWait(driver, 90);

        driver.get("https://qacloud.experitest.com");
        WebElement username = myFindElement(By.xpath("//*[@name=\"username\"]"));
//        WebElement username = myFindElement(By.xpath("//*[@name=\"username\"]"));
        username.sendKeys("ariel");
        myFindElement(By.xpath("//*[@name=\"password\"]")).sendKeys("Experitest2012");
        myFindElement(By.xpath("//*[@name=\"login\"]")).click();
        //Firefox open a new tab when try to click on log in button
        if (browser.getplatform().equals(BrowserType.FIREFOX) &&
                (driver.getWindowHandles().size() > 1 ||
                        !driver.getCurrentUrl().contains("qacloud.experitest.com"))) {
            myFindElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.TAB);
            myFindElement(By.xpath("//*[@name=\"password\"]")).sendKeys(Keys.ENTER);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"side-menu\"]/li[5]"))).click();
//        myFindElement(By.xpath("//*[@id=\"side-menu\"]/li[5]")).click();
        int i = 0;
        while (i < 5) {
            myFindElement(By.xpath("//*[@id=\"side-menu\"]/li[2]")).click();
            myFindElement(By.xpath("//*[@id=\"side-menu\"]/li[4]")).click();
            i++;
        }

        //Wikipedia
        driver.get("https://en.wikipedia.org/wiki/Special:Random");
        String currentUrl = driver.getCurrentUrl();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"searchInput\"]"))).sendKeys("Experitest");

        int j = 0;
        driver.get("http://the-internet.herokuapp.com");
        myFindElement(By.xpath("//*[@id=\"content\"]/ul/li[5]/a")).click();
        while (j < 3) {
            myFindElement(By.xpath("//*[@class=\"button\"]")).click();
//            if (driver.findElements(By.xpath("//*[@checked]")).size() > 0) {
//                myFindElement(By.xpath("//*[@id=\"checkboxes\"]/input[1]")).click();
//            } else {
//                myFindElement(By.xpath("//*[@id=\"checkboxes\"]/input[2]")).click();
//            }
            j++;
        }
        if (!browser.getBrowserName().equals(BrowserType.SAFARI)) {
            driver.navigate().back();
            myFindElement(By.xpath("//*[@id=\"content\"]/ul/li[9]/a")).click();
            driver.get("http://the-internet.herokuapp.com/dropdown");
            WebElement dropdown = myFindElement(By.xpath("//*[@id=\"dropdown\"]"));
            dropdown.click();
            myFindElement(By.xpath("//*[@id=\"dropdown\"]/option[2]")).click();
            driver.navigate().back();
            driver.navigate().back();
            myFindElement(By.xpath("//*[@id=\"content\"]/ul/li[16]/a")).click();
            JavascriptExecutor jse = driver;
            jse.executeScript("scroll(0, 500);"); //Down
            jse.executeScript("scroll(0, 250);"); //Down
            jse.executeScript("scroll(0, -250);");//Up
            jse.executeScript("scroll(0, -600);");//Up
        }

        driver.get("https://www.google.com");
        WebElement searchBar = myFindElement(By.xpath("//*[@name=\"q\"]"));
        searchBar.click();
        driver.getPageSource();
        searchBar.sendKeys("Jerusalem wiki");
        searchBar.sendKeys(Keys.ENTER);

        //BasicGoogleTest Operations
        driver.getTitle();
        driver.navigate().back();
        driver.navigate().refresh();
        driver.navigate().forward();
        driver.navigate().to(currentUrl);
        try {
            driver.navigate().to(new URL(currentUrl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //uploadFile
        driver.setFileDetector(new LocalFileDetector());
        driver.get("http://the-internet.herokuapp.com/upload");
        WebElement input = myFindElement(By.id("file-upload"));
        WebElement submit = myFindElement(By.id("file-submit"));

        String txtfile = System.getProperty("user.dir")+File.separator+"src"+ File.separator+"main"+ File.separator+"java"+ File.separator+"Project"+ File.separator+"Selenium"+ File.separator+"SeleniumTests"+ File.separator+"simple.txt";
        input.sendKeys(txtfile);
        submit.click();

//        driver.manage().window().setSize(new Dimension(500, 500));
//        driver.manage().window().maximize();
//        driver.manage().window().setSize(new Dimension(500, 500));
//        driver.manage().window().fullscreen();
//        driver.manage().window().getSize();
        //------- Add 11.6.18 --------------
        driver.manage().deleteAllCookies();

        //How to get HPROF file :)
//        if (!browserName.equals(BrowserType.SAFARI)) {
//            for (String logTypes :
//                    driver.manage().logs().getAvailableLogTypes()) {
//                driver.manage().logs().get(logTypes);
//            }
//        }
    }

    @Override
    public void ChooseAppDC(){
        dc.setCapability("useWaitUntil", USE_WAIT_UNTIL);//default is 300

        String browserType = browser.getplatform();
        testName = this.getClass().getSimpleName() + " " +browser.getplatform();
        dc.setCapability("testName",testName);
    }
}
