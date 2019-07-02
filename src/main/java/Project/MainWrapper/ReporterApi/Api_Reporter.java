package Project.MainWrapper.ReporterApi;

import Project.Main;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

public class Api_Reporter {

    private static HttpResponse<String> responseString;
//    private HttpResponse<InputStream> responseInputStream;


    public static String GetTestResultStatus(String TestID) {
        String status = null;
        System.out.println("GetTestResultStatus API_Reporter Url is " + TestID);
        if (TestID.equals(null)) {
            System.out.println("GetTestResultStatus NULL!!");
        } else {
            String url = Main.cloudUser.getCloudFullAdress() + "/reporter" + "/api/tests/" + TestID;
            System.out.println("API_Reporter Url is (GetTestResultStatus): " + url);

            try {
                responseString = Unirest.get(url)
                        .basicAuth(Main.cloudUser.userName, Main.cloudUser.Password)
                        .header("content-type", "application/json")
                        .asString();
                //   System.out.println(responseString.getBody());
                status = parsingJson(responseString.getBody(), "status");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            return status;

    }



    public static String getReporterVersion() {
        String ReporterVersion="";
        String url = Main.cloudUser.getCloudFullAdress()+"/reporter/api/serverConfiguration";
        System.out.println("API_Reporter Url is: (getReporterVersion) "+url);

        try {
            responseString = Unirest.get(url)
                    .basicAuth(Main.cloudUser.userName, Main.cloudUser.Password)
                    .header("content-type", "application/json")
                    .asString();
            //   System.out.println(responseString.getBody());
            ReporterVersion = parsingJson(responseString.getBody(),"version");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ReporterVersion;

    }

    public static String getCloudVersion() {
        String CloudVersion="";
        String url = Main.cloudUser.getCloudFullAdress()+"/api/v2/status/capabilities";
        System.out.println("API_Cloud Url is: "+url);

        try {
            responseString = Unirest.get(url)
                    .basicAuth(Main.cloudUser.userName, Main.cloudUser.Password)
                    .header("content-type", "application/json")
                    .asString();
            //   System.out.println(responseString.getBody());
            CloudVersion = parsingJson(responseString.getBody(),"version");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CloudVersion;

    }

    public static JSONArray getAgentsFromCloud() {
        JSONArray jsonArr=null;
        String url = Main.cloudUser.getCloudFullAdress()+"/api/v2/agents";
        try {
            responseString = Unirest.get(url)
                    .basicAuth(Main.cloudUser.userName, Main.cloudUser.Password)
                    .header("content-type", "application/json")
                    .asString();

            if(responseString.getBody().contains("Access is denied")){
                return jsonArr;
            }else {
                jsonArr = new JSONArray(responseString.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonArr;

    }


    public static void main(String[] args) {
        Main.initTheMain();
        System.out.println(getCloudVersion());
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
