package Project.MainWrapper.googleSheets.SummaryReportIntegration;

import Project.Main;
import Project.MainWrapper.googleSheets.GoogleSheetsIntegration;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.gson.Gson;
import org.mortbay.util.ajax.JSON;

import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

public class GoogleSheetSummaryReportIntegration extends GoogleSheetsIntegration {


    public static int searchCurrentRunLineNumber(String runName) throws IOException {
        System.out.println("Searching this run line number in the summary report");
        int currentRunLineNumber = -1;
        ValueRange R = sheetsService.spreadsheets().values()
                .get(Main.mainSpreadShit,"B1:B").execute();

        int countLines =  R.getValues().size();
        int countColumn = ((ArrayList)((ArrayList) R.getValues()).get(0)).size();

        for (int row = 0; row <countLines; row++) {
            try {
                // System.out.println(R.getValues().get(row).get(0));
                if(R.getValues().get(row).get(0).equals(runName)){
                    currentRunLineNumber=row+1;

                    break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        if(currentRunLineNumber>=0) {
            System.out.println("Found it! - this run line number in summary report is: " + currentRunLineNumber);
        }
        else{
            System.out.println("Can't find this run line number in summary report");
        }
        return currentRunLineNumber;

    }

    //mainSpreadShit
    public static void AddNumberToTheFirstColumn(int lineNumber) throws IOException {
        //AddNumberToTheFirstColumn
        //RunIndex
        ValueRange body1 = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(lineNumber)));
        UpdateValuesResponse RunIndex = sheetsService.spreadsheets().values()
                .update(Main.mainSpreadShit, "A"+lineNumber, body1)
                .setValueInputOption("RAW")
                .execute();

    }



    //Add summary report to the run line
    public static void addSummaryReportLine_CloudInfo(int lineNumber, String CloudVersion, String SeeTestVersion, String ReporterVersion ) throws IOException {
        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(

                        Arrays.asList(CloudVersion, SeeTestVersion, ReporterVersion)));
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(Main.mainSpreadShit, "D"+lineNumber, body)
                .setValueInputOption("RAW")
                .execute();

        addSummaryReportLine_ReporterView(lineNumber);
    }

    //Add summary report - add reporter view
    //java encode url parameters
    public static void addSummaryReportLine_ReporterView(int lineNumber) throws IOException {
        String reportURL = Main.cloudUser.getCloudFullAdress()+"/reporter/#/test-view-view/100?testView=";
        String reportParameters = "{\"byKey\": \"RunName\",  \"byKeyValue\": \""+ Main.startTime+"\"}";
        String ReportLink = reportURL+ URLEncoder.encode(reportParameters, "UTF-8");
        System.out.println(ReportLink);


        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(ReportLink)));
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(Main.mainSpreadShit, "P"+lineNumber, body)
                .setValueInputOption("RAW")
                .execute();
    }



    //Add summary report to the run line
    public static void addSummaryReportLine_TestsResultsInfo(int lineNumber, String EndTime, int Total_TestsNum, int Total_TestFailed, int  Android_TestsNum ,int Android_TestFailed, int IOS_TestsNum ,int IOS_TestFailed , int Selenium_TestsNum ,int Selenium_TestFailed) throws IOException {
       // getValueFromCell_int(Main.mainSpreadShit, lineNumber, 5);
        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(EndTime, Total_TestsNum, Total_TestFailed, Android_TestsNum ,Android_TestFailed, IOS_TestsNum ,IOS_TestFailed , Selenium_TestsNum ,Selenium_TestFailed)));
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(Main.mainSpreadShit, "G"+lineNumber, body)
                .setValueInputOption("RAW")
                .execute();
    }



    //Add summary report to the run line
    public static void addSummaryReportLine(int lineNumber, String CloudVersion, String SeeTestVersion, String ReporterVersion, String EndTime, String Total_TestsNum, String Total_TestFailed, String Android_TestsNum ,String Android_TestFailed, String IOS_TestsNum ,String IOS_TestFailed, String LinkToReporterView, String LinkToCloudLogs ) throws IOException {

        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(

                        Arrays.asList(CloudVersion, SeeTestVersion, ReporterVersion, EndTime, Total_TestsNum, Total_TestFailed, Android_TestsNum ,Android_TestFailed, IOS_TestsNum ,IOS_TestFailed, LinkToReporterView, LinkToCloudLogs)));
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(Main.mainSpreadShit, "D"+lineNumber, body)
                .setValueInputOption("RAW")
                .execute();
    }




}
