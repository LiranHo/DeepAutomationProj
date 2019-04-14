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
    private static Sheets sheetsService;




    //Setup the sheets and ask for permission
    public static void sheets_setup() throws GeneralSecurityException, IOException {
        sheetsService = SheetsServiceUtil.getSheetsService();
    }

    public static void addTowAppending(String time, String type, String testName, String deviceSN,String Agent, String status,String startTime, String endTime,String testDuring,String SessionID, String reportURL , String exception ) throws IOException {
        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(time, type, testName,testName, deviceSN , Agent, status,startTime, endTime, testDuring,SessionID, reportURL , exception)));
        AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                .append(Main.SPREADSHEET_ID, "A1", appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();

        ValueRange total = appendResult.getUpdates().getUpdatedData();


    }

    public static void add_SPREADSHEET_ID(String fileName, String SPREADSHEET_ID) throws IOException {
        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList("",fileName, "https://docs.google.com/spreadsheets/d/"+SPREADSHEET_ID+"/edit#gid=0")));
        AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                .append(Main.mainSpreadShit, "A1", appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();

        ValueRange total = appendResult.getUpdates().getUpdatedData();


    }

        public static void addFirstFirstRow() throws IOException {
        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList("Line 1","line2","line3","line4")));
//                        Arrays.asList("Line 2", "30"),
//                        Arrays.asList("Line 3", "10"),
//                        Arrays.asList("Line 4"),
//                        Arrays.asList("Line 5", "202"),
//                        Arrays.asList("Line 6", "5"))).setMajorDimension("COLUMNS");

        //https://developers.google.com/apis-explorer/?hl=en_US#p/sheets/v4/
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(Main.SPREADSHEET_ID, "A1", body)
                //.update(SPREADSHEET_ID, "A1", body)
                .setValueInputOption("RAW")
                .execute();
    }

    //CreateNewSpreadSheet
    public static void newSpreadSheet(String title) throws IOException {
        Spreadsheet spreadSheet = new Spreadsheet().setProperties(
                new SpreadsheetProperties().setTitle(title));

        spreadSheet = sheetsService.spreadsheets().create(spreadSheet)
                .setFields("spreadsheetId")
                .execute();
        System.out.println("Spreadsheet ID: " + spreadSheet.getSpreadsheetId());
        String SS_ID  = spreadSheet.getSpreadsheetId();
        String SS_URL  = spreadSheet.getSpreadsheetUrl();

     //   spreadSheet.setSheets(sheets);

    }

    public static String newSheet(String title) throws IOException {
        Spreadsheet spreadSheet = new Spreadsheet().setProperties(
                new SpreadsheetProperties().setTitle(title));

        spreadSheet = sheetsService.spreadsheets().create(spreadSheet)
                .setFields("spreadsheetId")
                .execute();

//        System.out.println("Spreadsheet ID: " + spreadSheet.getSpreadsheetId());
        String SS_ID  = spreadSheet.getSpreadsheetId();
        java.util.List<Sheet> sheets =  new ArrayList<>();
        sheets.add(new Sheet().setProperties(new SheetProperties ().setTitle("title"))
        );
        spreadSheet.setSheets(sheets);

        return SS_ID;
    }


    public static void ChangeTitle(String title) throws IOException {
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

//    public static void main(String[] args) throws IOException, GeneralSecurityException {
//        sheets_setup();
//        addTowAppending("1","","","","","","","","","","","");
//        addNewTab("a");
//    }

    public static void addNewTab(String title) throws IOException {
        int sheetId = 1;
        String destinationSpreadsheetId = "";

        CopySheetToAnotherSpreadsheetRequest requestBody = new CopySheetToAnotherSpreadsheetRequest();
        requestBody.setDestinationSpreadsheetId(Main.SPREADSHEET_ID);

        Sheets.Spreadsheets.SheetsOperations.CopyTo request =
                sheetsService.spreadsheets().sheets().copyTo(Main.SPREADSHEET_ID, sheetId, requestBody);

        SheetProperties response = request.execute();

//
//        List<Request> requests = new ArrayList<>();
//        requests.add(new Request()
//                .setUpdateSpreadsheetProperties(new UpdateSpreadsheetPropertiesRequest()
//                        .setProperties(SheetProperties.sheetId)
//                                .setTitle(title))
//                        .setFields("title")));
//
//
//        BatchUpdateSpreadsheetRequest body =
//                new BatchUpdateSpreadsheetRequest().setRequests(requests);
//        BatchUpdateSpreadsheetResponse response =
//                sheetsService.spreadsheets().batchUpdate(SPREADSHEET_ID, body).execute();

    }


        //Find And Replace
    public static void Find_And_Replace(String title) throws IOException {
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
    }
