package Project.MainWrapper.googleSheets;

import Project.Main;
import Project.MainWrapper.googleSheets.SummaryReportIntegration.GoogleSheetSummaryReportIntegration;
import Project.TestWrapper.Agent;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class SummaryReport {
    private static int Total_TestsNum = 0;
    private static int Total_TestsFailed = 0;

    private static int Android_TestsNum = 0;
    private static int Android_TestsFailed = 0;

    private static int IOS_TestsNum = 0;
    private static int IOS_TestsFailed = 0;

    private static int Selenium_TestsNum = 0;
    private static int Selenium_TestsFailed = 0;


    private static String Agents_Per_list_print = "";



    public static void updateTestsResult_SummeryReport() throws IOException {
        String EndTime = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date());
        GoogleSheetSummaryReportIntegration.addSummaryReportLine_TestsResultsInfo(Main.ThisRunLineInSummaryReport,EndTime, Total_TestsNum,
                Total_TestsFailed, Android_TestsNum,  Android_TestsFailed, IOS_TestsNum,IOS_TestsFailed , Agents_Per_list_print,Selenium_TestsNum,Selenium_TestsFailed);
    }

    public static void add1_Total_TestsNum(){
        Total_TestsNum++;
    }

    public static void add1_Total_TestsFailed(){
        Total_TestsFailed++;
    }

    //---
    public static void add1_Android_TestsNum(){
        Android_TestsNum++;
    }

    public static void add1_Android_TestsFailed(){
        Android_TestsFailed++;
    }

    //----

    public static void add1_IOS_TestsNum(){
        IOS_TestsNum++;
    }

    public static void add1_IOS_TestsFailed(){
        IOS_TestsFailed++;
    }


    //----
    public static void getAgentsPer(){
        Agents_Per_list_print="";
        for (Agent agent : Main.agents) {
            Agents_Per_list_print += agent.getAgentName()+": \t"+"Total: "+agent.getNumberOfTests_Total()+"\t| Tests Passed(%): "+agent.getAgentPassedPercentage()+"%" +"\t| Incomplete: " +agent.getAgentIncompletePercentage()+"% "+Main.delimiter;
        }
    }


    //----

    public static void add1_Selenium_TestsNum(){
        Selenium_TestsNum++;
    }

    public static void add1_Selenium_TestsFailed(){
        Selenium_TestsFailed++;
    }


    public static void main(String[] args) {

    }


}
