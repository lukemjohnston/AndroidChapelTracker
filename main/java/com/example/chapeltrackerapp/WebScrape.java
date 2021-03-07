package com.example.chapeltrackerapp;

import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WebScrape extends AppCompatActivity {

    ChapelsDatabaseHelper chapelsDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Allows network requests to be made in the main thread
        // This is a bad, hopefully temporary solution
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        System.out.println("In main");


        //SharedPreferences credentials = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //String username = credentials.getString(USER_NAME, null);
        //String password = credentials.getString(PASSWORD, null);
       // updateCompletedChapelInfo(username, password);
    }

    private static final String TAG = "MyActivity";

    public static void updateCompletedChapelInfo (String username, String password){
        try {
            // Logs into MyAccount and redirects to Chapel Report page

            Connection.Response myaccountLoginResponse = Jsoup.connect("https://login.biola.edu/cas/login?service=https%3A%2F%2Fmyaccount.biola.edu%2Fchapel_report")
                    .referrer("https://myaccount.biola.edu/chapel_report")
                    .timeout(10 * 1000)
                    .method(Connection.Method.GET)
                    .followRedirects(true)
                    .execute();

            Document loginDoc = myaccountLoginResponse.parse();

            System.out.println("Fetched login page for chapel report.");

            // This gets the cookies from the response, which we will post to the action URL
            Map<String, String> mapLoginPageCookies = myaccountLoginResponse.cookies();

            //String authentication = loginDoc.select("div.fields > input[name=\"execution\"]").attr("value");
            //System.out.println(authentication);

            // Selects and saves authentication token for login
            String authToken = loginDoc.select("div.fields > input[name=\"execution\"]")
                    .attr("value");

            // Makes a data map that contains all of the parameters and their values found in the login form
            Map<String, String> mapParams = new HashMap<>();
            mapParams.put("username", username);
            mapParams.put("password", password);
            mapParams.put("execution", authToken);
            mapParams.put("_eventId", "submit");
            mapParams.put("submit", "Log In");

            String strActionURL = "https://login.biola.edu/cas/login?service=https%3A%2F%2Fmyaccount.biola.edu%2Ftasks";

            Connection.Response responsePostLogin = Jsoup.connect(strActionURL)
                    //referrer will be the login page's URL
                    .referrer("https://mail.rediff.com/cgi-bin/login.cgi")
                    //connect and read time out
                    .timeout(10 * 1000)
                    //post parameters
                    .data(mapParams)
                    //cookies received from login page
                    .cookies(mapLoginPageCookies)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    //many websites redirects the user after login, so follow them
                    .followRedirects(true)
                    .execute();

            System.out.println("HTTP Status Code: " + responsePostLogin.statusCode());

            // Parse the response document
            Document loginResponseDoc = responsePostLogin.parse();
            // System.out.println(loginResponseDoc);

            // Get cookies from response
            Map<String, String> mapLoggedInCookies = responsePostLogin.cookies();


            // Scrapes stat data from Chapel Report

            Element statTable = loginResponseDoc.select("table").get(0);

            // Chapel credits completed
            int completedCredit = Integer.parseInt(statTable.select("tr:eq(1) > td:eq(1) > p:last-of-type").get(0).text().replaceAll("\\D+",""));
            System.out.println(completedCredit);

            // Total chapel credits required in semester
            int creditRequired = Integer.parseInt(statTable.select("tr:eq(0) > td:eq(1)").text());
            System.out.println(creditRequired);

            // Chapel credits still remaining to be completed
            int creditRemaining = creditRequired - completedCredit;
            System.out.println(creditRemaining);

            // Number of chapel opportunities in semester
            int opportunitiesTotal = Integer.parseInt(statTable.select("tr:eq(3) > td:eq(1) > p:eq(0)").text().replaceAll("\\D+",""));
            System.out.println(opportunitiesTotal);

            // Number of chapel opportunities past
            int opportunitiesPast = Integer.parseInt(statTable.select("tr:eq(3) > td:eq(1) > p:eq(1)").text().replaceAll("\\D+",""));
            System.out.println(opportunitiesPast);

            // Number of chapel opportunities still remaining in semester
            int opportunitiesRemaining = Integer.parseInt(statTable.select("tr:eq(3) > td:eq(1) > p:eq(2)").text().replaceAll("\\D+",""));
            System.out.println(opportunitiesRemaining);


            // Scrapes chapels completed data from Chapel Report

            Element completedTable = loginResponseDoc.select("table").get(1);

            for(Element completedChapel : completedTable.select("tr")){
                // Checks to see if record is for a chapel, not for a Torrey Conference
                // TODO improve this check
                if(!completedChapel.select("td:eq(2)").text().equals("Chapel")) {
                }
                // todo add each completed chapel to database or edit existing chapel to be marked as completed
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void updateAllChapels(ArrayList<String> titleList, ArrayList<String> dateList, ArrayList<String> timeList, ArrayList<String> locationList, ArrayList<String> presenterList) {
        System.out.println("Starting to update chapels");
        final String chapelScheduleURL = "https://www.biola.edu/chapel";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            final Document document = Jsoup.connect(chapelScheduleURL).get();

            // System.out.println(document.outerHtml());

            for (Element titles : document.select("h3.title")) {
                final String title = titles.text();
                titleList.add(title);
                System.out.println(title);
            }
            for (Element datetimes : document.select("div.datetime")) {
                final String datetime = datetimes.text();

                SimpleDateFormat parser = new SimpleDateFormat("EEE, MMM d, hh:mm a");
                Date dateTime = parser.parse(datetime);

                SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd");
                String formattedDate = dateFormatter.format(dateTime);
                SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                String formattedTime = timeFormatter.format(dateTime);

                dateList.add(formattedDate);
                timeList.add(formattedTime);

                System.out.println(formattedDate);
                System.out.println(formattedTime);
            }
            for (Element locations : document.select("div.location")) {
                final String location = locations.text();
                locationList.add(location);
                System.out.println(location);
            }
            for (Element presenters : document.select("h4.subtitle")) {
                final String presenter = presenters.text();
                presenterList.add(presenter);
                System.out.println(presenter);
            }

            for (int i = 0; i < titleList.size(); i++){
                System.out.println(titleList.get(i) + dateList.get(i) + timeList.get(i) + locationList.get(i));
                // + presenterList.get(i)
            }



        } catch (Exception ex) {
            //Toast.makeText(WebScrape.this, "Caught", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }
}

