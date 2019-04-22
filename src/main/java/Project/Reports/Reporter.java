package Project.Reports;


import Project.Main;
import Project.MainWrapper.googleSheets.GoogleSheetsIntegration;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

//Singleton
public class Reporter extends ReportBasics{

    private static Reporter report= null;

    static SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yy-HH:mm:ss,SS");

    PrintWriter file;

    //singleton constructor (defined as private)
    private Reporter(String FileName , String projDir) {
        //Crete new report
        this.file=CreateReportFile(projDir, FileName+" ; "+ Main.startTime, "csv");
    }


    //Create report only once
    public static Reporter Reporter(String FileName , String projDir){
        if(report == null)
            report=new Reporter(FileName , projDir);
        return report;
    }


    //TODO: fix the report!
    //T: add row to main report
    public void addRowToReport(String type, String testName, String deviceSN,String Agent, String status, String ReporterStatus,String startTime, String endTime, String testDuring,String SessionID, String reportURL , String exception){
        Date currentTime = new Date();
        String line;
        currentTime.getTime();
        line = currentTime+","+
                type+","+
                testName+","+
                deviceSN+","+
                Agent+","+
                status+","+
                ReporterStatus+","+
                startTime+","+
                endTime+","+
                testDuring+","+
                SessionID+","+
                reportURL+","+
                exception;
        System.out.println(line);
        file.println(line);
        file.flush();


            if(Main.WriteToGoogleSheet &&!Thread.currentThread().getName().toLowerCase().equals("main")){
            try {
                //Add row to google sheet
                GoogleSheetsIntegration.addTowAppending(String.valueOf(currentTime), type, testName, deviceSN,Agent, status,ReporterStatus,startTime, endTime, testDuring,SessionID, reportURL , exception);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
