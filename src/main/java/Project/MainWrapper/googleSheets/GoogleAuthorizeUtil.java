package Project.MainWrapper.googleSheets;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;


import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;


//https://developers.google.com/sheets/api/quickstart/java
//https://www.baeldung.com/google-sheets-java-client
//https://github.com/eugenp/tutorials/blob/1609a9a5de267c69f26dc80027424a74d3e37fe6/libraries/src/main/java/com/baeldung/google/sheets/GoogleAuthorizeUtil.java


public class GoogleAuthorizeUtil {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS,SheetsScopes.DRIVE);
    static String Scope = "https://www.googleapis.com/auth/content";
    private static final String CREDENTIALS_FILE_PATH = "/google-sheets-client-secret.json";
    private static final String authorize_FILE_PATH = "src/main/resources/quickstart-1554884801478-60fa6b1ec788.json";

//    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/2/sheets.googleapis.com-java-quickstart.json");

    public static Credential authorize() throws IOException, GeneralSecurityException {

        // Load client secrets.
        InputStream in = GoogleAuthorizeUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets, SCOPES).setDataStoreFactory(new MemoryDataStoreFactory())
                .setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

//T: Other Option:
//        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(authorize_FILE_PATH))
//        .createScoped(SCOPES);

        return credential;
    }


}