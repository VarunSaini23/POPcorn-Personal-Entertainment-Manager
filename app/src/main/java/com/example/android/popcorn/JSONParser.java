package com.example.android.popcorn;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.popcorn.models.SampleData;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    private JSONParser() {
    }


    public static List<SampleData> fetchBookData(String RequestUrl){
        URL url = createUrl(RequestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e ){
            Log.e("http problem","problem making http request",e);
        }

        List<SampleData> data = extractFeatureFromJson(jsonResponse);
        return data;

    }

    /**
     * Return a list of {@link SampleData} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<SampleData> extractFeatureFromJson(String BookJSON) {

        // Create an empty ArrayList that we can start adding earthquakes to

        if (TextUtils.isEmpty(BookJSON)){
            return null;}
        List<SampleData> data = new ArrayList<>();


        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject jsonRootObject = new JSONObject(BookJSON);
            JSONArray results = jsonRootObject.getJSONArray("results");

            for (int i=0;i<results.length();i++){
                JSONObject obj = results.getJSONObject(i);
                String title = obj.getString("title");
                Double popularity = obj.getDouble("popularity");
                Double vote_average = obj.getDouble("vote_average");
                String release_date = obj.getString("release_date");

                String imageurl = "https://image.tmdb.org/t/p/w500";
                String poster_path= obj.getString("poster_path");
                imageurl = imageurl + poster_path;





//                data.add(new SampleData(title,popularity,vote_average,release_date,imageurl));


                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                // build up a list of Earthquake objects with the corresponding data.

            }}

        catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return data;
    }

    public static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);

        }catch (MalformedURLException e){
            Log.e("url build","Problem Building Url",e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(6000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e("url build","Problem Building url" + urlConnection.getResponseCode());
            }

        }catch (IOException e){
            Log.e("retrieving data","Problem retrieving the earthquake JSON results.", e);

        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}