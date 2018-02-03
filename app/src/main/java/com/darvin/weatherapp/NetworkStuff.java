package com.darvin.weatherapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NetworkStuff {

    private static String LOG_TAG = NetworkStuff.class.getSimpleName();

    static Weather getData(String location) throws IOException, JSONException {
        Weather weather;
        URL url = createURL(location);
        String response = makeHttpRequest(url);
        weather = jsonResponse(response);
        return weather;
    }

    private static URL createURL(String location) throws MalformedURLException {
        final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
        final String API_KEY = "appid";
        final String QUERY_PARAMETER = "q";
        final String QUERY_UNITS = "units";
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY, "your-api-key")
                .appendQueryParameter(QUERY_PARAMETER, location)
                .appendQueryParameter(QUERY_UNITS, "metric").build();

        return new URL(uri.toString());
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String response = "";

        // If the URL is null, then return early.
        if (url == null) {
            return response;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                response = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();

            if (inputStream != null) {
                inputStream.close();
            }
        }
        return response;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }

    private static Weather jsonResponse(String response) throws JSONException {
        System.out.println(response);
        JSONObject baseObject = new JSONObject(response);
        String location = baseObject.getString("name");
        JSONArray weatherArray = baseObject.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);
        String main = weatherObject.getString("main");
        String description = weatherObject.getString("description");
        String icon = weatherObject.getString("icon");
        JSONObject mainObject = baseObject.getJSONObject("main");
        String temp = mainObject.getString("temp");

        return new Weather(main, description, icon, temp, location);
    }
}
