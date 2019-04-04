package Project;

import Project.Settings.CloudUsers;
import Project.TestWrapper.AfterClassExtension;
import Project.TestWrapper.Device;
import com.experitest.appium.SeeTestClient;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import com.experitest.client.MobileListener;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@ExtendWith(AfterClassExtension.class)
@DisplayName("Base Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    public String reportURL = "ReportURL";
    public Device device = new Device(Main.RUN_ONE_DEVICE_SN);
    public String sessionID;
    protected String testName;
    protected long startTimePerDevice;
    protected String testStartTime;
    protected String testEndTime;
    public long testStartTime_calculate;
    protected long testDuring;
    public String path;
    public static String FolderinnerDeviceDirPath;
   // GridClient gridClient;
    //STA settings
    protected static String host = "localhost";
    protected static int port = 8889;
   // protected Client client = null;

    protected AppiumDriver driver = null;
    public SeeTestClient client = null;

    //protected IOSDriver<IOSElement> IOSDriver = null;
    //protected AndroidDriver<AndroidElement> AndroidDriver = null;
    public DesiredCapabilities dc = new DesiredCapabilities();



    @BeforeAll
    public void Init(){
        //check if main is not initialized and if so init it
        if (Thread.currentThread().getName().toLowerCase().equals("main")) {
            System.out.println("RUNNING MAIN THREAD");
            Main.initTheMain();
        }


        System.out.println("Before All for device: "+device.getSerialnumber());
        startTimePerDevice = System.currentTimeMillis();
        String DeviceSN = Thread.currentThread().getName();
        if (Main.searchDeviceBySN(DeviceSN) != null) {
            device = Main.searchDeviceBySN(DeviceSN);
        }
        testName = device.getSerialnumber();
        System.out.println(new Date() + "\t" + device.getSerialnumber() + "\tBaseTest BeforeAll - device " + Thread.currentThread().getName());
        FolderinnerDeviceDirPath = device.getDeviceFolderPath();

    }

    @BeforeEach
    public void setUp() throws Exception {
        System.out.println("Before Each for device: "+device.getSerialnumber());
        Main.sout("Info", new Date() + "\t" + device.getSerialnumber() + "\tBaseTest BeforeEach - device " + device.getSerialnumber());

        testStartTime = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date());
        testStartTime_calculate = System.currentTimeMillis();

        createDriver();


        //TODO:

    }

    @AfterEach
    public void tearDown() {
        System.out.println("After Each for device: "+device.getSerialnumber());

        try {
            if (driver == null) {
                Main.sout("Error!", "Tear Down - Driver is null, do nothing " + device.getSerialnumber());
                return;
            }

            Main.sout("Info","Report URL for device: "+device.getSerialnumber()+"  "+driver.getCapabilities().getCapability("reportUrl"));
            reportURL= (String) driver.getCapabilities().getCapability("reportUrl");

            try {
                CollectSupportDataFromBeep(this.getClass().getName());
            }catch (Exception e){
                Main.sout("Exception!","CollectSupportDataFromBeep failed"+ e.getMessage());
            }
            Main.sout("Info"," driver.quit() Start: "+device.getSerialnumber());
            driver.quit();
            Main.sout("Info"," driver.quit() End: "+device.getSerialnumber());


        } catch (Exception e) {
            Main.sout("Exception", "tearDown Failed \t" + e.getMessage());

            /** try it out **/
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            Main.sout("Exception", "BaseTest TearDown printStackTrace: " + "\t" + exceptionAsString);
            /*****/

        }

        testEndTime = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date());



    }



    //Choose the application to work with in the test, if doesn't overided it will use nothing
    public void ChooseAppDC(){
        Main.sout("Info","Doesn't use any application");
    }




    public void createDriver() throws Exception {
        System.out.println("Crete Driver for device: "+device.getSerialnumber());

        dc.setCapability("RunName", Main.startTime);
        dc.setCapability("testName", testName);
        dc.setCapability("deviceQuery", "@serialNumber='"+device.getSerialnumber()+"'");
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
            if (device.isAndroid()) {
//                dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
//                dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
//                dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
                Main.sout("Info","Starting to find Android device "+device.getSerialnumber());
                try {
                    driver = new AndroidDriver<>(new URL( Main.cloudUser.getCloudFullAdress()+"/wd/hub"), dc);
                } catch (MalformedURLException e) {
                    Main.sout("Exception!","Failed to start Android device "+device.getSerialnumber());
                    return;
                }

                Main.sout("Info","Succession to find Android device "+device.getSerialnumber());

            }
            else { //Device IOS
//                    dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
//                    dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
                    Main.sout("Info", "Starting to find IOS device " + device.getSerialnumber());

                try {
                    driver = new IOSDriver<>(new URL( Main.cloudUser.getCloudFullAdress()+"/wd/hub"), dc);
                } catch (MalformedURLException e) {
                    Main.sout("Exception!","Failed to start IOS device "+device.getSerialnumber());
                    return;
                }

                    Main.sout("Info", "Succession to find IOS device " + device.getSerialnumber());
            }
            try {
                client = new SeeTestClient(driver);
            } catch (Exception e) {
                Main.sout("Exception!","Failed to start SeeTestClient device "+device.getSerialnumber());
            }

        } else{ //Not Grid
            Main.sout("Error!","Can't run on Not Grid Tests");
            throw new Exception("Can't run not Grid tests for now");
        }


        //Add relevant info
        System.out.println("Client SessionID: " + driver.getSessionId());
        driver.getRemoteAddress();

        sessionID = String.valueOf(driver.getSessionId());

    }


    protected void CollectSupportDataFromBeep(String theClassThatActivateMe) {
        System.out.println("** BeeperControl check in class " + theClassThatActivateMe + " \t Main.CollectSupportDataVar = " + Main.CollectSupportDataVar.get());
        if(Main.CollectSupportDataVar.compareAndSet(true,false)){ //check if true and than change it to false
            System.out.println("CollectSupportData from device: "+device.getSerialnumber());
            String collectSupportDataPATH = Main.innerDirectoryPath + File.separator+"SupportData" + new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date()) + ".zip";
            System.out.println("** Start to collectSupportData from beeperControl activation," + Main.delimiter + "collectSupportData Path is: " + collectSupportDataPATH);
            client.collectSupportData(collectSupportDataPATH, "", "", "", "", "", true, false);
        }
    }

    public String getTestName() {
        return this.testName;
    }



}
