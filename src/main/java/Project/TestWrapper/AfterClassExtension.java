package Project.TestWrapper;


import Project.BaseTest;
import Project.Main;
import Project.MainWrapper.ReporterApi.Api_Reporter;
import Project.MainWrapper.googleSheets.SummaryReport;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;
public class AfterClassExtension implements AfterEachCallback {
    boolean deviceOS_isIOS;
    Agent agent;

    @Override
    public void afterEach(ExtensionContext context) throws Exception {

        Main.sout("Info","afterTestExecution for device: "+ Thread.currentThread().getName());
        BaseTest baseTest = ((BaseTest)context.getTestInstance().get());
        Boolean testResult = !context.getExecutionException().isPresent(); //true - SUCCESS, false- FAILED

        Optional<Throwable> executionException=context.getExecutionException();
        String sessionID = baseTest.sessionID;
        String testName = context.getDisplayName();

        String deviceSN= baseTest.device.getSerialnumber();
        agent = baseTest.device.getAgent();
        deviceOS_isIOS= baseTest.device.isIOS();
        String ReporterStatus = "null";
        try {
            ReporterStatus = Api_Reporter.GetTestResultStatus(baseTest.testID);
        }catch (Exception e){
            System.out.println("ReporterStatus is null");
//            ReporterStatus += " - "+ e.getMessage();
        }

        String reportPath = baseTest.reportURL;
        if(!reportPath.contains("http") && !reportPath.contains("ReportURL")) {
            reportPath = reportPath + "/index.html";
        }
        String EndTime=String.valueOf(baseTest.testEndTime);
        String StartTime=String.valueOf(baseTest.testStartTime);
        long testDuring =System.currentTimeMillis() - baseTest.testStartTime_calculate;


        Main.countTests++;


        boolean incompleteTest = false;
        if(ReporterStatus.equals("null")||ReporterStatus.equals("Incomplete")){
            incompleteTest = true;
        }

        if(testResult.equals(false)){
            System.err.println("afterTestExecution - FAIL \t"+"+Thread.currentThread().getName() "+Thread.currentThread().getName()+"\t devicesn: "+deviceSN);
            String error=executionException.toString().replaceAll("\n"," | ");
            Main.report.addRowToReport(getDeviceOSinString(),testName, deviceSN,agent.getAgentName(),String.valueOf(testResult), ReporterStatus,StartTime ,EndTime,calculateTestDuring(testDuring),sessionID,reportPath,error);
//           System.out.println("the test failed");
            Main.countTests_fail++;

            if(incompleteTest){
                addTestResultToAgent("Incomplete");
            }else {
                addTestResultToAgent(false);
            }
            addToSummaryReport(false);
        }

        if(testResult.equals(true)){
            System.err.println("afterTestExecution - PASS \t"+"+Thread.currentThread().getName() "+Thread.currentThread().getName()+"\t device sn: "+deviceSN);
            Main.report.addRowToReport(getDeviceOSinString(),testName, deviceSN,agent.getAgentName(),String.valueOf(testResult),ReporterStatus, StartTime , EndTime,calculateTestDuring(testDuring),sessionID,reportPath,"");
//            System.out.println("the test passed");
            Main.countTests_pass++;

            if(incompleteTest){
                addTestResultToAgent("Incomplete");
                addToSummaryReport(false);
            }
            else {
                addTestResultToAgent(true);
                addToSummaryReport(true);
            }

        }
    }


    public void addTestResultToAgent(boolean result){
        agent.addTestToAgent(result);
    }

    public void addTestResultToAgent(String result){
        agent.addTestToAgent(result);
    }



    protected void addToSummaryReport(boolean result){
        try {
            SummaryReport.getAgentsPer();
        }catch (Exception e){
            System.out.println("SummaryReport.getAgentsPer failed "+e.getMessage());
        }
        SummaryReport.add1_Total_TestsNum();
        if(!result){
            SummaryReport.add1_Total_TestsFailed();
        }
        if(deviceOS_isIOS){
            SummaryReport.add1_IOS_TestsNum();
            if(!result){
                SummaryReport.add1_IOS_TestsFailed();
            }

            }else{ //Android
            SummaryReport.add1_Android_TestsNum();
            if(!result){
                SummaryReport.add1_Android_TestsFailed();
            }
        }

    }

    protected String getDeviceOSinString(){
        if(deviceOS_isIOS){
            return "IOS";
        }
        else{
            return "Android";
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

    protected String calculateTestDuring_old(long TestDuring){
        System.out.println("Calculating test during");
        TestDuring=TestDuring/1000;
        String Units=" Sec";
        if (TestDuring>60 && TestDuring<60*60){
            long second = TestDuring%60;
            TestDuring=TestDuring/60 ;
            Units=" Min";
        }
        else
        if (TestDuring>60*60){
            TestDuring=TestDuring/(60*60);
            Units=" Hour";
        }

        System.out.println(String.valueOf(TestDuring)+Units);
        return String.valueOf(TestDuring)+Units;
    }



}
