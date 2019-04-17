package Project.MainWrapper.ReporterApi;

import Project.Main;
import Project.Settings.CloudUsers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Api_Reporter {

    private static HttpResponse<String> responseString;
//    private HttpResponse<InputStream> responseInputStream;


    public static String GetTestResultStatus(String TestID) {
        String status = null;
        String url = Main.cloudUser.getCloudFullAdress()+"/reporter" + "/api/tests/"+TestID;
        System.out.println("API_Reporter Url is: "+url);

        try {
            responseString = Unirest.get(url)
                    .basicAuth(Main.cloudUser.userName, Main.cloudUser.Password)
                    .header("content-type", "application/json")
                    .asString();
         //   System.out.println(responseString.getBody());
            status = parsingJson(responseString.getBody(),"status");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }


    public static String parsingJson(String Json_String,String key){
        System.out.println("Json string is "+Json_String);
        String returnValue=null;
        try {
            JSONObject Json_obj = new JSONObject(Json_String);
            returnValue = Json_obj.getString(key);
        }catch (Exception e){
            System.out.println("Cant parse JSON "+ e.getMessage());

        }

        return returnValue;
    }


}
