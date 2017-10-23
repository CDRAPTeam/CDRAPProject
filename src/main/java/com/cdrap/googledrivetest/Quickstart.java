package com.cdrap.googledrivetest;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Quickstart {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Test01(to see api)";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES = 
        Arrays.asList(SheetsScopes.DRIVE);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            Quickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Sheets service = getSheetsService();
        // Prints the names and majors of students in a sample spreadsheet:
        // https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
	//https://docs.google.com/spreadsheets/d/13UzFFWK6-Mpkguk7HXmbMjWzoF73D_EE988rZg6GcZk/edit?usp=sharing
        String spreadsheetId = "13UzFFWK6-Mpkguk7HXmbMjWzoF73D_EE988rZg6GcZk";
        String range = "Class Data!A2:F20";
        ValueRange response = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute();
        List<List<Object>> values = response.getValues();
        
        if (values == null || values.size() == 0) {
            System.out.println("No data found.");
        } else {
          System.out.println("Name, Major");
          for (List row : values) {
            // Print columns A and E, which correspond to indices 0 and 4.
            System.out.printf("%s, %s\n", row.get(0), row.get(5));
          }
        }
		List<List<Object>> values2 = Arrays.asList(
	        	Arrays.asList(
	                	(Object)"treee"
	                	)
	        	// Additional rows ...
	        	);
		CellFormat form = new CellFormat();
		Color c = new Color();
		form.setBackgroundColor(c);
		
		ValueRange body = new ValueRange().setValues(values2).setRange("Class Data!A2:A2");
		List<ValueRange> bodier = Arrays.asList(body);
		
		BatchUpdateValuesRequest bodyz = new BatchUpdateValuesRequest().setValueInputOption("USER_ENTERED").setData(bodier);
		ConditionalFormatRule rule = new ConditionalFormatRule();
		rule.setRanges(Arrays.asList(new GridRange().setSheetId(0).setStartColumnIndex(2).setEndColumnIndex(3).setStartRowIndex(3).setEndRowIndex(4)));
		rule.setUnknownKeys(form);
		bodyz.setUnknownKeys(rule);
		
		//service.spreadsheets().values().batchUpdate(spreadsheetId, bodyz).execute();
		List<GridRange> ranges = Arrays.asList(new GridRange()
		        .setSheetId(0)
		        .setStartRowIndex(1)
		        .setEndRowIndex(11)
		        .setStartColumnIndex(0)
		        .setEndColumnIndex(4)
		);
		//new Request().setAppendCells(new AppendCellRequest());
		//new Request().setAddSheet(new AddSheetRequest().setProperties(new SheetProperties().setGridProperties(new GridProperties().get)))
		List<Request> requests = Arrays.asList(
				new Request().setDeleteConditionalFormatRule(new DeleteConditionalFormatRuleRequest().setIndex(0)),
		        new Request().setAddConditionalFormatRule(new AddConditionalFormatRuleRequest()
		                .setRule(new ConditionalFormatRule()
		                        .setRanges(ranges)
		                        .setBooleanRule(new BooleanRule()
		                                .setCondition(new BooleanCondition()
		                                        .setType("CUSTOM_FORMULA")
		                                        .setValues(Collections.singletonList(
		                                                new ConditionValue().setUserEnteredValue("=1")
		                                        ))
		                                )
		                                .setFormat(new CellFormat().setTextFormat(
		                                        new TextFormat().setForegroundColor(new Color().setRed(0.2f)))
		                                			.setBackgroundColor(new Color().setRed(.1f).setGreen(0.4f).setBlue(1f)))
		                        )
		                )
		                .setIndex(0)
		        )/*,
		        new Request().setAddConditionalFormatRule(new AddConditionalFormatRuleRequest()
		                .setRule(new ConditionalFormatRule()
		                        .setRanges(ranges)
		                        .setBooleanRule(new BooleanRule()
		                                .setCondition(new BooleanCondition()
		                                        .setType("CUSTOM_FORMULA")
		                                        .setValues(Collections.singletonList(
		                                                new ConditionValue()
		                                                        .setUserEnteredValue("=1")
		                                        ))
		                                )
		                                .setFormat(new CellFormat().setBackgroundColor(
		                                        new Color().setRed(.1f).setGreen(0.4f).setBlue(1f)
		                                ))
		                        )
		                )
		                .setIndex(0)
		        
		        )*/
		);
		
		BatchUpdateSpreadsheetRequest body3 = new BatchUpdateSpreadsheetRequest().setRequests(requests);
		BatchUpdateSpreadsheetResponse result = service.spreadsheets()
		        .batchUpdate(spreadsheetId, body3)
		        .execute();
		List<Request> r2 = Arrays.asList(new Request());
		com.google.api.services.sheets.v4.model.BatchGetValuesResponse rs = new BatchGetValuesResponse().setSpreadsheetId(spreadsheetId);
	
		System.out.println(result);
	//sheets.setNamedRanges(Arrays.asList(ranger));
	//placeinto folder below
	//https://drive.google.com/drive/folders/0BxbhyjMk-FVpUGtrY090YlFOZm8?usp=sharing
		//service.spreadsheets().create(sheets).execute();
		//service.spreadsheets().create(sheets);
    }
    


}