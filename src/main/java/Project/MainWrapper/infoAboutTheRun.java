
package Project.MainWrapper;

import Project.Main;
import Project.MainWrapper.googleSheets.SummaryReportIntegration.GoogleSheetSummaryReportIntegration;

import java.io.IOException;

import static Project.MainWrapper.ReporterApi.Api_Reporter.*;

public class infoAboutTheRun {
    public static String cloudVersion="";
    public static String seeTestVersion="";
    public static String reporterVersion="";

    public static void addInfoAboutCloudVersion_notGoogleSheet() throws IOException {
        Main.Cloud_version_number= getCloudVersion();
    }

    public static void addInfoAboutCloud() throws IOException {
        System.out.println("Adding info about the cloud for this run");
        reporterVersion = getReporterVersion();
        cloudVersion = getCloudVersion();
        Main.Cloud_version_number= cloudVersion;
        GoogleSheetSummaryReportIntegration.addSummaryReportLine_CloudInfo(Main.ThisRunLineInSummaryReport, cloudVersion, seeTestVersion, reporterVersion);

    }


}
