package Project.MainWrapper.googleSheets;

import Project.Main;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//https://www.baeldung.com/google-sheets-java-client
//https://docs.google.com/spreadsheets/d/1U7IY_cj-ussOLovn1joM5X7elxRV46mPu3G_YpiXOHQ/edit#gid=1374062528

public class GoogleSheetsIntegration {
    public static Sheets sheetsService;


    //Setup the sheets and ask for permission
    public static void sheets_setup() throws GeneralSecurityException, IOException {
        sheetsService = SheetsServiceUtil.getSheetsService();
    }


    //SPREADSHEET
    public static void addTowAppending(String time, String type, String testName, String deviceSN, String Agent, String status, String Reportstatus, String startTime, String endTime, String testDuring, String SessionID, String reportURL, String exception) throws IOException {
        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(time, type, testName, deviceSN, Agent, status, Reportstatus, startTime, endTime, testDuring, SessionID, reportURL, exception)));
        AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                .append(Main.SPREADSHEET_ID, "A1", appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();

        ValueRange total = appendResult.getUpdates().getUpdatedData();

    }

    // add SPREADSHEET ID to summary report
    public static void add_SPREADSHEET_ID(String fileName, String SPREADSHEET_ID) throws IOException {
        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList("", fileName, "https://docs.google.com/spreadsheets/d/" + SPREADSHEET_ID + "/edit#gid=0")));
        AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                .append(Main.mainSpreadShit, "A1", appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();

        ValueRange total = appendResult.getUpdates().getUpdatedData();

    }

    //CreateNewSpreadSheet
    public static void newSpreadSheet(String title) throws IOException {
        Spreadsheet spreadSheet = new Spreadsheet().setProperties(
                new SpreadsheetProperties().setTitle(title));

        spreadSheet = sheetsService.spreadsheets().create(spreadSheet)
                .setFields("spreadsheetId")
                .execute();
        System.out.println("Spreadsheet ID: " + spreadSheet.getSpreadsheetId());
        String SS_ID = spreadSheet.getSpreadsheetId();
        String SS_URL = spreadSheet.getSpreadsheetUrl();

        //   spreadSheet.setSheets(sheets);

    }

    public static String newSheet(String title) throws IOException {
        Spreadsheet spreadSheet = new Spreadsheet().setProperties(
                new SpreadsheetProperties().setTitle(title));

        spreadSheet = sheetsService.spreadsheets().create(spreadSheet)
                .setFields("spreadsheetId")
                .execute();

//        System.out.println("Spreadsheet ID: " + spreadSheet.getSpreadsheetId());
        String SS_ID = spreadSheet.getSpreadsheetId();
        java.util.List<Sheet> sheets = new ArrayList<>();
        sheets.add(new Sheet().setProperties(new SheetProperties().setTitle("title"))
        );
        spreadSheet.setSheets(sheets);

        return SS_ID;
    }


    public static void changeTitle(String title) throws IOException {
        List<Request> requests = new ArrayList<>();
// Change the spreadsheet's title.
        requests.add(new Request()
                .setUpdateSpreadsheetProperties(new UpdateSpreadsheetPropertiesRequest()
                        .setProperties(new SpreadsheetProperties()
                                .setTitle(title))
                        .setFields("title")));

        BatchUpdateSpreadsheetRequest body =
                new BatchUpdateSpreadsheetRequest().setRequests(requests);
        BatchUpdateSpreadsheetResponse response =
                sheetsService.spreadsheets().batchUpdate(Main.SPREADSHEET_ID, body).execute();
        //FindReplaceResponse findReplaceResponse = response.getReplies().get(1).getFindReplace();
        //System.out.printf("%d replacements made.", findReplaceResponse.getOccurrencesChanged());

    }


    //Find And Replace
    public static void find_And_Replace(String title) throws IOException {
        List<Request> requests = new ArrayList<>();
// Change the spreadsheet's title.
        requests.add(new Request()
                .setUpdateSpreadsheetProperties(new UpdateSpreadsheetPropertiesRequest()
                        .setProperties(new SpreadsheetProperties()
                                .setTitle(title))
                        .setFields("title")));
// Find and replace text.
        requests.add(new Request()
                .setFindReplace(new FindReplaceRequest()
                        .setFind("a")
                        .setReplacement("b")
                        .setAllSheets(true)));
// Add additional requests (operations) ...

        BatchUpdateSpreadsheetRequest body =
                new BatchUpdateSpreadsheetRequest().setRequests(requests);
        BatchUpdateSpreadsheetResponse response =
                sheetsService.spreadsheets().batchUpdate(Main.SPREADSHEET_ID, body).execute();
        FindReplaceResponse findReplaceResponse = response.getReplies().get(1).getFindReplace();
        System.out.printf("%d replacements made.", findReplaceResponse.getOccurrencesChanged());


    }


    public static String getValueFromCell_string(String SpreadSheet, int line, int column) throws IOException {
        ValueRange R = sheetsService.spreadsheets().values()
                .get(SpreadSheet, "A1:O").execute();

        return (String) R.getValues().get(line).get(column);

    }

    public static int getValueFromCell_int(String SpreadSheet, int line, int column) throws IOException {
        ValueRange R = sheetsService.spreadsheets().values()
                .get(SpreadSheet, "A1:O").execute();

        return (int) R.getValues().get(line).get(column);

    }

    //Print All Sheet
    public static void printAllSheet() throws IOException {
        ValueRange R = sheetsService.spreadsheets().values()
                .get(Main.SPREADSHEET_ID, "A1:O").execute();

        int countLines = R.getValues().size();
        int countColumn = ((ArrayList) ((ArrayList) R.getValues()).get(0)).size();

        for (int row = 0; row < countLines; row++) {
            for (int column = 0; column < countColumn; column++) {
                try {
                    System.out.println(R.getValues().get(row).get(column));
                } catch (Exception e) {
                }
            }
            System.out.println("##EndOfRow" + row + "##");
        }

    }


}
