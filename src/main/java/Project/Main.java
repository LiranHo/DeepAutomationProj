package Project;

import Project.MainWrapper.googleSheets.GoogleSheetsIntegration;
import Project.MainWrapper.googleSheets.SummaryReportIntegration.GoogleSheetSummaryReportIntegration;
import Project.MainWrapper.infoAboutTheRun;
import Project.Reports.Files;
import Project.Reports.Reporter;
import Project.Settings.BeeperControl;
import Project.Settings.CloudUsers;
import Project.Settings.ProjectSettingPerUser;
import Project.Settings.TestSuites;
import Project.TestWrapper.Device;
import com.experitest.appium.SeeTestClient;
import com.google.api.services.sheets.v4.Sheets;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static Project.MainWrapper.InitDeviceList.initDevicesList;

public class Main {

    final protected static String RUN_ONE_DEVICE_SN = "14bdd0fd9904";
    public final static int NUMBER_OF_DEVICES_TO_RUN = 12; //choose 0 to run ALL devices

    //**Add Report To google Sheets**
    public static Boolean WriteToGoogleSheet = true;
    public static String mainSpreadShit = "1U7IY_cj-ussOLovn1joM5X7elxRV46mPu3G_YpiXOHQ";


    public static void initTheMain() {

        EnterInput = false;
        //T: ***Init The Test***
        //T: 1. Choose the platform to run (if Grid is true choose the cloud user)
        Grid = true;
        cloudUser = CloudUsers.LiranDeepCloud;
//        cloudUser = CloudUsers.LiranCloud;
        //T: 2. Choose on what devices to run: (Can add devices SN or Name (without ADB:))
        //TODO: make sure devices can't be added twice
        //TODO: fix that adb: or ios: also work
        //TODO: add option to run on random X devices
        chooseSpesificDevices = false;
        Choosedevices.add("14bdd0fd9904");
      //  Choosedevices.add("87345845f042238c45d80e66c7b95b48766337eb");
        //Choosedevices.add("34c8cc257d54");

        //T: 3. Choose the run length (Run by time or choose number of Rounds - Or choose the length time you want)
        Runby_NumberOfRounds = true; /**/
        NumberOfRoundsToRun = 20000;
        TimeToRun = 60 * 60 * 3; //Seconds * minutes * hours
        //T: 4. choose classes or packages to run with
        testsSuites = TestSuites.AllTest;

        //T: 5. choose some properties
        BatteryMonitoring = false;

        //T: ***Start To Prepare The Test***
        if(startTime == null) {
            startTime = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date());
        }
        innerDirectoryPath = createNewDir(projectBaseDirectory, startTime); //create new directory for this test run
        //T: Init reports
        // INIT report and add first raw titles
        report = Reporter.Reporter("MainReport", innerDirectoryPath);
        report.addRowToReport("Type", "Test Name", "Device SN", "Agent","Status", "Reporter Status","Start Time" ,"End Time","Test During", "Session ID", "Report URL", "Exception");

        // INIT info file
        infoFile = new Files("Init Info", innerDirectoryPath);
        ErrorFile = new Files("Error File", innerDirectoryPath);

    }

    public static void main(String[] args) throws Exception {
        startTime = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date());
        if(WriteToGoogleSheet){
            System.out.println("The Main File Report is: "+"https://docs.google.com/spreadsheets/d/"+mainSpreadShit+"/edit#gid=1716146997");
            System.out.println("***");
            System.out.println("***");
            System.out.println("APPROVE THE GOOGLE SHEETS PERMISSIONS");

            GoogleSheetsIntegration.sheets_setup();
           String ID =  GoogleSheetsIntegration.newSheet(String.valueOf(startTime));
            SPREADSHEET_ID = ID;
            GoogleSheetsIntegration.add_SPREADSHEET_ID(startTime, SPREADSHEET_ID);
            System.out.println("GoogleSheetID:  " + "https://docs.google.com/spreadsheets/d/" + SPREADSHEET_ID + "/edit#gid=0");

            ThisRunLineInSummaryReport = GoogleSheetSummaryReportIntegration.searchCurrentRunLineNumber(startTime);
        }
        initTheMain();

        if(WriteToGoogleSheet) {
            //Add Cloud Info to the SummaryReport
            GoogleSheetSummaryReportIntegration.AddNumberToTheFirstColumn(ThisRunLineInSummaryReport);
            infoAboutTheRun.addInfoAboutCloud();
        }


        //NOTE: EnterInput
        if (EnterInput)
            Project.MainWrapper.GetInput.getInputFromUser();
        else
            Project.MainWrapper.GetInput.printCurrentTunProperties();



        System.err.println("###STARTING...###");


        //T: get devices list and create Hashmap
        String devicesInitInfo = "";
        try {
            devicesInitInfo = initDevicesList();
            //Add devices info to info file
            infoFile.addRowToReport(false, devicesInitInfo);
        } catch (Exception e) {
            System.err.println("Failed to initDevicesList");
            infoFile.addRowToReport(true, "*** Failed to initDevicesList *** " + delimiter + e.getMessage(), true);
            ErrorFile.addRowToReport(true, "*** Failed to initDevicesList *** " + delimiter + e.getMessage(), true);
            report.addRowToReport("FAILURE", "initDevicesList", "", "","Fail", "","0", "","","","","");

            e.printStackTrace();
        }


        //Create Run Info File:
        //add devices info
        infoFile.addRowToReport(false, cloudUser.toString());

        //T: 4: Create threads for each device and start to Run
        if (Main.devices.size() <= 0)
            throw new Exception("Devices list is 0");
        ExecutorService executorService = Executors.newCachedThreadPool();
//        ArrayList<Future> futures = new ArrayList<>();

        int i=0;
        for (Device device : devices) {
            if(NUMBER_OF_DEVICES_TO_RUN>0) {
                if( i>=NUMBER_OF_DEVICES_TO_RUN)
                    break;
                i++;

            }

                System.out.println("starting device - " + device.getSerialnumber());
                Runner r = new Runner(device);
                System.out.println("Runner is up for device - " + device.getSerialnumber());
//            futures.add(executorService.submit(r));
                executorService.execute(r);


        }



        //T: Create CollectSupportData Thread usinneg beeperControl
        //Run collect support data only if the test is long enough
        collectSupportData();


        System.err.println("Started All Threads");


        executorService.shutdown();
        if(executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)) {
            System.out.println("Finished all threads");
        } else {
            System.out.println("Timeout reached before all threads finished");
        }


//        if (collectSupportDataThread != null) {
//            collectSupportDataThread.terminate();
////        collectSupportDataThread.interrupt();
//
//            Main.sout("Info!","collectSupportDataThread.terminate()");
//        }

        //stop the collectSupportData- beep
        if(beep!=null) {
            beep.Terminate();
//            while(!beep.beeperHandle.isCancelled()) {
//                Main.sout("Info!", "beep.Terminate() and is isCancelled()= " + beep.beeperHandle.isCancelled());
//                beep.beeperHandle.cancel(true);
//            }
            Main.sout("Info!", "beep.Terminate()" );
        }

        //Add tests info to the info file
        String countTestsInfo = "=========================" + delimiter +
                "Count Test Info" + delimiter +
                "countTests: " + countTests + delimiter +
                "countTests_fail: " + countTests_fail + delimiter +
                "countTests_pass: " + countTests_pass + delimiter;
        infoFile.addRowToReport(true, countTestsInfo);
//        System.exit(0);

    }


    //***Init Test Vars***
    //**Devices**
    public static List<Device> devices = new ArrayList<>(); // the devices list which we run on
    public static List<String> Choosedevices = new ArrayList<>(); // the devices SN the user want to run on
    public static boolean chooseSpesificDevices; //Choose specific devices or run on all connected devices
    public static boolean Runby_NumberOfRounds; //Choose nubmer of rounds or decided the time length you want to run
    public static int NumberOfRoundsToRun; //Choose nubmer of rounds
    public static int TimeToRun; //decided the time length you want to run: Hours * Min
    public static int numberOfDeviceInThisRun = 0; //decided the time length you want to run: Hours * Min


    public static boolean Devices;
    public static boolean Grid = false;
    public static Boolean EnterInput;


    //Tests
    public static TestSuites testsSuites;


    //**Local**
//    public static Client client = null;
    protected AndroidDriver<AndroidElement> driverAndroid = null;
    protected IOSDriver<AndroidElement> driverIOS = null;


    public static String local_host = "localhost";
    public static int local_port = 8889;

    //**Grid**
    public static CloudUsers cloudUser;


    //**Report**
    public static String projectBaseDirectory = ProjectSettingPerUser.projectBaseDirectory;
    //public static String Repository_project = "C:\\Users\\liran.hochman\\workspace\\project2";
    public static String innerDirectoryPath = "";
    public static String startTime;
    public static Reporter report;
    public static Files infoFile;
    public static Files ErrorFile;
    public static int countTests = 0;
    public static int countTests_fail = 0;
    public static int countTests_pass = 0;
    private static BeeperControl beep;
    final public static int CollectEveryX_inMin=30; //Optimal is 30 MIN
    public static AtomicBoolean CollectSupportDataVar = new AtomicBoolean(false);
    public static AtomicInteger SummaryReportVar = new AtomicInteger(0);
    public static String PrintDevicesInfo;
    public static String PrintDeviceSN;

    //**Google Sheets report**
    public static String SPREADSHEET_ID = "";
    public static int ThisRunLineInSummaryReport = -1;



    //**Applications install paths**
    public static String EriBankInstallOnComputerPath = "E:\\Files - Liran - 2\\Applications_apk\\EriBank\\eribank.apk";
    public static String EriBankLaunchName = "com.experitest.ExperiBank/.LoginActivity";
    public static String EriBankPackageName = "com.experitest.ExperiBank";

    public static String UiCatalogInstallOnComputerPath = "E:\\Files - Liran - 2\\Applications_apk\\UiCatalog\\UICatalog.apk";
    public static String UiCatalogLaunchName = "com.experitest.uicatalog/.MainActivity";
    public static String UiCatalogPackageName = "com.experitest.uicatalog";

    public static String EriBankLaunchName_old = "com.experitest.eribank/com.experitest.ExperiBank.LoginActivity";
    public static String EriBankPackageName_old = "com.experitest.eribank";

    //**Run Other Properties**
    public static Boolean BatteryMonitoring;


    //create new directory, get the directory path and the new folder name
    public static String createNewDir(String path, String folderName) {
        File newDir = new File(path + File.separator + folderName);
        String createdPath = path + File.separator + folderName;
        //create
        if (!newDir.exists()) {
            System.out.println("creating directory: " + newDir.getName());
            try {
                newDir.mkdirs();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
        return createdPath;
    }


    public static Device searchDeviceBySN(String SN) {
        for (Device device : devices) {
            if (device.getSerialnumber().equals(SN)) {
                return device;
            }
        }
        return null;
    }

    //run collect support data only if the test is long enough
    private static void collectSupportData() { //with beep
        if ((Runby_NumberOfRounds && NumberOfRoundsToRun >= 10) || (!Runby_NumberOfRounds && TimeToRun >= (60 * 60 * 2))) {

//            CollectSupportDataVar=true;
            CollectSupportDataVar.set(true);
            //Check beeper control
            beep = new BeeperControl();
            beep.WakeEveryHour();

            // ############
            //disable collect support data for now
            System.err.println("(!) collectSupportDataThread is disabled");
            //collectSupportDataThread = new CollectSupportDataThread();
            //collectSupportDataThread.start();
        }

    }
    public static void sout(String type,String deviceSN, String output)throws NullPointerException { //print and add to file "error file"
        //if using ! in the type - it will be printes in red in the console
        if(type.toLowerCase().contains("!")){
            System.err.println(Main.ErrorFile.addRowToReport(type,deviceSN,output));
        }else
            System.out.println(Main.ErrorFile.addRowToReport(type,deviceSN,output));
    }



    public static void sout(String type, String output)throws NullPointerException{ //print and add to file "error file"
        //if using ! in the type - it will be printes in red in the console
        if(type.toLowerCase().contains("!")){
            System.err.println(Main.ErrorFile.addRowToReport(type,output));
        }else
            System.out.println(Main.ErrorFile.addRowToReport(type,output));
    }

    //Finals - aid variables
    public static final String delimiter = "\r\n";




}
