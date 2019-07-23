package Project.Selenium;

import Project.BaseTest;
import Project.Main;
import Project.Selenium.SeleniumTests.AllBrowsersTypeTestsSuite;
import Project.TestWrapper.Browser;
import Project.TestWrapper.BrowsersAndDevicesHandle.DisableDevices;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SelectPackages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@ExtendWith(Selenium_AfterClassExtension.class)
@DisplayName("Base Test Browser")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectPackages("SeleniumTests")
@DisableDevices
public class BaseTest_Browser extends CreateDriverForBrowser {

    public String reportURL = "ReportURL";
    public String sessionID;
    protected String testName;
    protected long startTimePerDevice;
    public String testStartTime;
    public String testEndTime;
    public long testStartTime_calculate;
    public String testID;
    public String thisRunBrowserType;

    public static Boolean isEdgeRunning=false;



    private URL url;
    protected long testDuring;
    public String path;
    public static String FolderinnerDeviceDirPath;

    protected RemoteWebDriver driver = null;

    public DesiredCapabilities dc = new DesiredCapabilities();
    public Browser browser = null;

    protected boolean USE_WAIT_UNTIL = false;//help to ignore from browsers issues
    protected WebDriverWait wait;



    @BeforeAll
    public void Init() {

        //   Main.initTheMain();

        System.out.println("Before All for Browser: ");
        startTimePerDevice = System.currentTimeMillis();
        String DeviceSN = Thread.currentThread().getName();

        if (Main.searchBrowserBySN(DeviceSN) != null) {
            browser = Main.searchBrowserBySN(DeviceSN);
        }

        System.out.println(new Date() + "\t" + browser.getSerialnumber() + "\tBaseTest BeforeAll - browser " + Thread.currentThread().getName());


    }

    @BeforeEach
    public void setUp() throws Exception {
        System.out.println("Before Each for browser: "+browser.getSerialnumber());
        Main.sout("Info", new Date() + "\t" + browser.getSerialnumber() + "\tBaseTest BeforeEach - browser " +browser.getSerialnumber());

        testStartTime = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new Date());
        testStartTime_calculate = System.currentTimeMillis();

        //Create Driver
        driver = createDriver(browser.getSerialnumber(), testName, dc);


        thisRunBrowserType = driver.getCapabilities().getCapability("browserName").toString();
        sessionID = String.valueOf(driver.getSessionId());


            if (browser!=null && driver!=null){
                initSeleniumDriverSettings();
            }

        testID = BaseTest.findTestID((String)driver.getCapabilities().getCapability("reportUrl"));
        System.out.println("This testID is"+testID);

    }

    @AfterEach
    public void tearDown() {
        System.out.println("After Each for browser: "+browser.getSerialnumber());

        try {
            if (driver == null) {
                Main.sout("Error!", "Tear Down - Driver is null, do nothing " + browser.getSerialnumber());

                return;
            }

            Main.sout("Info","Report URL for browser: "+browser.getSerialnumber()+"  "+driver.getCapabilities().getCapability("reportUrl"));
            reportURL= (String) driver.getCapabilities().getCapability("reportUrl");

            //remove the browser type from RunningBrowsersTypes list

            if(driver.getCapabilities().getCapability("browserName").toString().equalsIgnoreCase("MicrosoftEdge")){
                isEdgeRunning=false;
            }
//
//            if(SearchBrowserTypeInAList(driver.getCapabilities().getCapability("browserName").toString(), true)){
//                System.out.println("browser type "+driver.getCapabilities().getCapability("browserName").toString()+" is removed succesfully");
//            }

            Main.sout("Info"," driver.quit() Start: "+browser.getSerialnumber());
            driver.quit();
            Main.sout("Info"," driver.quit() End: "+browser.getSerialnumber());


        } catch (Exception e) {
            Main.sout("Exception", "tearDown Failed \t" + e.getMessage());

            /** try it out **/
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            Main.sout("Exception", "BaseTest TearDown printStackTrace: " + "\t" + exceptionAsString);
            /*****/

        }

        testEndTime = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new Date());
    }




    public void initSeleniumDriverSettings() {

        browser.setBrowserInfo(driver.getCapabilities().getVersion(), String.valueOf(driver.getCapabilities().getPlatform()), (String)driver.getCapabilities().getCapability("agentName"),  driver.getCapabilities().getBrowserName());
//        dc.getVersion();
//        dc.getPlatform();
//        dc.getBrowserName();
//        dc.getCapability("reportUrl");
//        dc.getCapability("sessionId");
//        dc.getCapability("agentName");
//        dc.getCapability("viewUrl");

    }

    public void chooseBrowserCapabilities(){
        String [] thisBrowserType = AllBrowsersTypeTestsSuite.browserType;
        String browserToRunOnce = "MicrosoftEdge";

        int length= thisBrowserType.length;
        synchronized (BaseTest_Browser.isEdgeRunning) {
            int rand = new Random().nextInt(BaseTest_Browser.isEdgeRunning ? thisBrowserType.length - 1 : thisBrowserType.length);
            browserType = thisBrowserType[rand];
            if (browserType.equalsIgnoreCase(browserToRunOnce)) {
                BaseTest_Browser.isEdgeRunning = true;
            }
        }

        System.out.println("Browser type is: " + browserType + "| " + browser.getSerialnumber());
        testName = this.getClass().getSimpleName() + " " + browserType;
        dc.setCapability("testName",testName);
        dc.setCapability(CapabilityType.BROWSER_NAME, browserType);


    }

        protected void CollectSupportDataFromBeep(String theClassThatActivateMe) {
        System.out.println("** BeeperControl check in class " + theClassThatActivateMe + " \t Main.CollectSupportDataVar = " + Main.CollectSupportDataVar.get());
        if(Main.CollectSupportDataVar.compareAndSet(true,false)){ //check if true and than change it to false
            System.out.println("CollectSupportData from browser: "+browser.getSerialnumber());
            String collectSupportDataPATH = Main.innerDirectoryPath + File.separator+"SupportData" + new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new Date()) + ".zip";
            System.out.println("** Start to collectSupportData from beeperControl activation," + Main.delimiter + "collectSupportData Path is: " + collectSupportDataPATH);

        }
    }


    public String getTestName() {
        return this.testName;
    }

    protected WebElement myFindElement(By xPath) {
        return USE_WAIT_UNTIL ? wait.until(ExpectedConditions.visibilityOfElementLocated(xPath)) : driver.findElement(xPath);
    }




        public static boolean ifNotNeedToBeTestedAsFailed(String e, String thisRunBrowserType){
            boolean ifBrowserTypeIsProblematic = thisRunBrowserType==null
                    ;
            if(e.contains("Could not start selenium grid test. No browser found matching the desired capabilities") && ifBrowserTypeIsProblematic){
                System.out.println("Browser type is: "+thisRunBrowserType);
                return true;
            }
            else{
                return false;
            }

        }


}
