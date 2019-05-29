package Project.Tests;

import Project.BaseTest;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.common.io.Resources;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class StorageCashTest extends BaseTest {

    @DisplayName("InstallNewApp")
    @Test
    public void InstallNewApp() throws Exception {

        String ApplicationName = "eribank";
        String ManifestName = "AndroidManifest";
//        CompileAndDecompile(ApplicationName, ManifestName);

        String newApplicationPath = new File(Resources.getResource("CreateMultipleAppsTools").getPath(),ApplicationName+"\\dist\\"+ApplicationName+".apk").getAbsolutePath();
        System.out.println("newApplicationPath: "+newApplicationPath);

//        String absoultePath = "E:\\FilesLiran2\\Experitest_Git\\DeepAutomationProj\\out\\production\\resources\\CreateMultipleAppsTools\\eribank\\dist\\eribank.apk";
//        client.install(newApplicationPath, false,false);
//        client.install("cloud:com.experitest.ExperiBank",false,false);
//        client.install("E:\\FilesLiran2\\Applications_apk\\UiCatalog\\UICatalog.apk",false,false);
        client.install("http://192.168.1.213:8090/guestAuth/repository/download/AndroidUtilities_AndroidAppsMaster/.lastSuccessful/eribank.apk",false,false);
    //    client.install(applicationPath, false,false);

    }


    public void CompileAndDecompile(String ApplicationName,String ManifestName ) throws Exception {
        String folder = Resources.getResource("CreateMultipleAppsTools").getPath();

        if(folder.charAt(0)=='/'){
            folder = folder.substring(1);
            System.out.println("folder "+ folder);
        }

        String url= folder + "/ChangeAppVersionScript.py";
        System.out.println("url "+url);


        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "python "+"\""+url +"\""+ " "+ApplicationName+" "+ManifestName);
//                "cmd.exe", "/c", "python "+"\""+url +"\""+ " "+ApplicationName+" "+ManifestName);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }
    }

}
