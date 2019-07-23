package Project.Selenium.SeleniumTests;

import Project.Selenium.BaseTest_Browser;
import Project.TestWrapper.Browser;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.BrowserType;

public class AllBrowsersTypeTestsSuite extends BaseTest_Browser {

    public static String [] browserType= {BrowserType.CHROME , BrowserType.FIREFOX , BrowserType.IE , BrowserType.SAFARI , BrowserType.EDGE};
//    public static String [] browserType= {BrowserType.CHROME, BrowserType.FIREFOX };
//    public static String [] browserType= { BrowserType.CHROME ,BrowserType.EDGE};
//    public static String [] browserType= {BrowserType.CHROME};

}
