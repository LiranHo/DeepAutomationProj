package Project.Selenium;


import Project.BaseTest;
import Project.Main;
import Project.MainWrapper.ReporterApi.Api_Reporter;
import Project.MainWrapper.googleSheets.SummaryReport;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;

public class Selenium_AfterClassExtension implements AfterEachCallback {
    boolean deviceOS_isIOS;

    @Override
    public void afterEach(ExtensionContext context) throws Exception {

        Main.sout("Info","afterTestExecution for browser: "+ Thread.currentThread().getName());
        BaseTest_Browser baseTest = ((BaseTest_Browser)context.getTestInstance().get());
        Boolean testResult = !context.getExecutionException().isPresent(); //true - SUCCESS, false- FAILED

        Optional<Throwable> executionException=context.getExecutionException();
        String sessionID = baseTest.sessionID;
        String testName = context.getDisplayName();

        String deviceSN= baseTest.browser.getSerialnumber();
        String Agent = baseTest.browser.getAgent();
        String thisRunBrowserType = baseTest.thisRunBrowserType;
//        Thread.sleep(2000);
        //String ReporterStatus = Api_Reporter.GetTestResultStatus(baseTest.testID);
        String ReporterStatus = "N/A"; //its take to much time to get this property and they all will be in incomplete status.

        String reportPath = baseTest.reportURL;
        if(!reportPath.contains("http") && !reportPath.contains("ReportURL")) {
            reportPath = reportPath + "/index.html";
        }
        String EndTime=String.valueOf(baseTest.testEndTime);
        String StartTime=String.valueOf(baseTest.testStartTime);
        long testDuring =System.currentTimeMillis() - baseTest.testStartTime_calculate;

        int count_Could_not_start_selenium_grid_test = 0;


        Main.countTests++;

        if(testResult.equals(false)){
            System.err.println("afterTestExecution - FAIL \t"+"+Thread.currentThread().getName() "+Thread.currentThread().getName()+"\t devicesn: "+deviceSN);
            String error=executionException.toString().replaceAll("\n"," | ");
            if(BaseTest_Browser.ifNotNeedToBeTestedAsFailed(error, thisRunBrowserType)){
                count_Could_not_start_selenium_grid_test++;
                System.out.println("BaseTest_Browser.ifNotNeedToBeTestedAsFailed is true - won't be count as failure");
                System.out.println("count_Could_not_start_selenium_grid_test: "+count_Could_not_start_selenium_grid_test);
                Main.report.addRowToReport(baseTest.thisRunBrowserType,testName, "Could not start selenium grid test #"+count_Could_not_start_selenium_grid_test,Agent,String.valueOf(testResult), ReporterStatus,StartTime ,EndTime,calculateTestDuring(testDuring),sessionID,reportPath,error);
            }
            else {

                Main.report.addRowToReport(baseTest.browser.getBrowserName(), testName, deviceSN, Agent, String.valueOf(testResult), ReporterStatus, StartTime, EndTime, calculateTestDuring(testDuring), sessionID, reportPath, error);
//           System.out.println("the test failed");
                Main.countTests_fail++;
                addToSummaryReport(false);
            }

        }

        if(testResult.equals(true)){
            System.err.println("afterTestExecution - PASS \t"+"+Thread.currentThread().getName() "+Thread.currentThread().getName()+"\t devicesn: "+deviceSN);
            Main.report.addRowToReport(baseTest.browser.getBrowserName(),testName, deviceSN,Agent,String.valueOf(testResult),ReporterStatus, StartTime , EndTime,calculateTestDuring(testDuring),sessionID,reportPath,"");
//            System.out.println("the test passed");
            Main.countTests_pass++;

            addToSummaryReport(true);

        }
    }


    protected void addToSummaryReport(boolean result){
        SummaryReport.add1_Selenium_TestsNum();
        if(!result){
            SummaryReport.add1_Selenium_TestsFailed();
        }
    }


    protected String calculateTestDuring(long TestDuring){
        System.out.println("Calculating test during");
        String TestDuringForPrint="";
        //Move from milliseconds to seconds
        TestDuring=TestDuring/1000;
        if(TestDuring<=60){
            TestDuringForPrint=String.valueOf(TestDuring)+ " Sec";
        }
        else
        if(TestDuring>60 && TestDuring<60*60){
            long Sec=TestDuring%60;
            TestDuring=TestDuring/60 ;
            TestDuringForPrint=String.valueOf(TestDuring)+ " Min "+String.valueOf(Sec)+" Sec";
        }
        else
        if(TestDuring>60*60){
            long Min=TestDuring%60;
            TestDuring=TestDuring/60;
            long Sec=TestDuring%60;
            TestDuring=TestDuring/60;
            TestDuringForPrint=String.valueOf(TestDuring)+ " Hour "+String.valueOf(Min)+ " Min "+String.valueOf(Sec)+" Sec";
        }

        System.out.println(TestDuringForPrint);
        return TestDuringForPrint;

    }


}
