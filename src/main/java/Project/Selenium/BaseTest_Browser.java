package Project.Selenium;

import Project.BaseTest;
import Project.Main;
import Project.TestWrapper.Browser;
import Project.TestWrapper.BrowsersAndDevicesHandle.DisableDevices;
import Project.TestWrapper.Device;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SelectPackages;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@ExtendWith(Selenium_AfterClassExtension.class)
@DisplayName("Base Test Browser")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectPackages("SeleniumTests")
@DisableDevices
public class BaseTest_Browser {

    public String reportURL = "ReportURL";
    public String sessionID;
    protected String testName;
    protected long startTimePerDevice;
    public String testStartTime;
    public String testEndTime;
    public long testStartTime_calculate;
    public String testID;

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

            createDriver();

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

    //Choose the application to work with in the test, if doesn't overided it will use nothing
    public void ChooseAppDC(){
        Main.sout("Info","Doesn't use any application");
//        dc.setCapability("testName", "Chrome Browser");
//        dc.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
//        dc.setCapability(CapabilityType.PLATFORM, Platform.ANY);

    }

    public void createDriver() throws Exception {
        System.out.println("Create Driver for browser: "+browser.getSerialnumber());

        dc.setCapability("RunName", Main.startTime);
        dc.setCapability("testName", testName);
      //  dc.setCapability("deviceQuery", "@serialNumber='"+browser.getSerialnumber()+"'");
        if(Main.cloudUser.getAccessKey().equals("0")){
            dc.setCapability("username", Main.cloudUser.getUserName());
            dc.setCapability("password", Main.cloudUser.getPassword());
        }else{
            dc.setCapability("accessKey", Main.cloudUser.getAccessKey());
        }
        dc.setCapability("projectName",  Main.cloudUser.getprojectName());
        dc.setCapability("reportFormat", "xml");

        if (Main.Grid) {
            ChooseAppDC();
                Main.sout("Info","Starting upload Browser "+browser.getSerialnumber());
                try {
                    driver = new RemoteWebDriver(new URL( Main.cloudUser.getCloudFullAdress()+"/wd/hub"), dc);
                } catch (MalformedURLException e) {
                    Main.sout("Exception!","Failed to start browser "+browser.getSerialnumber());
                    return;
                }

                Main.sout("Info","Succession to find browser "+browser.getSerialnumber());


        } else{ //Not Grid
            Main.sout("Error!","Can't run on Not Grid Tests");
            throw new Exception("Can't run not Grid tests for now");
        }


        //Add relevant info
        System.out.println("Client SessionID: " + driver.getSessionId());
        //driver.getRemoteAddress();

        sessionID = String.valueOf(driver.getSessionId());

    }

    public void initSeleniumDriverSettings() {
        browser.setBrowserInfo(dc.getVersion(), String.valueOf(dc.getPlatform()), (String)dc.getCapability("agentName"),  dc.getBrowserName());
//        dc.getVersion();
//        dc.getPlatform();
//        dc.getBrowserName();
//        dc.getCapability("reportUrl");
//        dc.getCapability("sessionId");
//        dc.getCapability("agentName");
//        dc.getCapability("viewUrl");

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


    }
