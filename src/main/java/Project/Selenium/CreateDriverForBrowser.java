package Project.Selenium;

import Project.Main;
import Project.Selenium.SeleniumTests.AllBrowsersTypeTestsSuite;
import Project.TestWrapper.Device;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class CreateDriverForBrowser {

    public String browserType;
    public boolean browserTypeOnlyOnce=false;




    public RemoteWebDriver createDriver(String serialNumber, String testName, DesiredCapabilities dc)  {
        RemoteWebDriver driver = null;

        System.out.println("Create Driver for browser: "+serialNumber);
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


        chooseBrowserCapabilities();
        //add chosen browser, and add atomic boolean to check if edge is up
        addCustomeCapabilities();
        System.out.println(browserType);


            Main.sout("Info","Starting upload Browser "+serialNumber);
            try {
                driver = new RemoteWebDriver(new URL( Main.cloudUser.getCloudFullAdress()+"/wd/hub"), dc);
            } catch (MalformedURLException e) {
                Main.sout("Exception!","Failed to start browser "+serialNumber);
            }

            Main.sout("Info","Succession to find browser "+serialNumber);



        return driver;

    }

    public void chooseBrowserCapabilities(){

    }


        //Choose the application to work with in the test, if doesn't override it will use nothing
    public void addCustomeCapabilities(){
        Main.sout("Info","Doesn't use any application");
//        dc.setCapability("testName", "Chrome Browser");
//        dc.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
//        dc.setCapability(CapabilityType.PLATFORM, Platform.ANY);

    }
}
