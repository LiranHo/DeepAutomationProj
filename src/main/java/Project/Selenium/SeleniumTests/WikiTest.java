package Project.Selenium.SeleniumTests;

import Project.Selenium.BaseTest_Browser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Random;

public class WikiTest extends BaseTest_Browser {
    String browserType;

    public void setBrowserType(String browserType){
        this.browserType= browserType;
    }

     @Test
    @DisplayName("WikiTest")
    public void WikiTest() throws InterruptedException {
        int i = 0;
         System.out.println("Start WikiTest for "+browser.getSerialnumber());
        //while (i < 30 * 4) {//6 mini
        while (i < 10 * 4) {//6 mini
            driver.get("https://en.wikipedia.org/wiki/Special:Random");
            Thread.sleep(2_000);
            i++;
        }
    }

    @Override
    public void ChooseAppDC(){
        int rand = new Random().nextInt(AllBrowsersTypeTestsSuite.browserType.length);
        browserType = AllBrowsersTypeTestsSuite.browserType[rand];
        testName = this.getClass().getSimpleName() + " " + browserType;
        dc.setCapability("testName",testName);
        dc.setCapability(CapabilityType.BROWSER_NAME, browserType);
    }

}
