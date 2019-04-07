package com.example.android.popcorn;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.android.popcorn.models.GenreLandData;
import com.example.android.popcorn.models.MovieDetailsData;
import com.example.android.popcorn.models.PersonFullData;
import com.example.android.popcorn.models.PersonListData;
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

public class GenreJSONParser<T> {

    public T t;

    public GenreJSONParser() {

    }




    public List<T> fetchBookData(String RequestUrl,Object t){



        URL url = createUrl(RequestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e ){
            Log.e("http problem","problem making http request",e);
        }

        Log.d("jsonresponse", jsonResponse);
        List<T> data = extractFeatureFromJson(jsonResponse,t);
        return data;

    }

    /**
     * Return a list of {@link SampleData} objects that has been built up from
     * parsing a JSON response.
     */
    public List<T> extractFeatureFromJson(String BookJSON,Object t) {

        // Create an empty ArrayList that we can start adding earthquakes to

        if (TextUtils.isEmpty(BookJSON)){
            return null;}
        List<T> data = new ArrayList<>();



        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
             if(t instanceof String) {
                 JSONObject jsonRootObject = new JSONObject(BookJSON);
                 JSONArray results = jsonRootObject.getJSONArray("results");
                 data.clear();
                 for (int i = 0; i < results.length(); i++) {

                     JSONObject obj = results.getJSONObject(i);
                     String title = obj.getString("title");
                     Double popularity = obj.getDouble("popularity");
                     Double vote_average = obj.getDouble("vote_average");
                     String release_date = obj.getString("release_date");
                     int id = obj.getInt("id");

                     String imageurl = "https://image.tmdb.org/t/p/w500";
                     String poster_path = obj.getString("poster_path");
                     imageurl = imageurl + poster_path;
                     data.add((T) new SampleData(title, popularity, vote_average, release_date, imageurl,id));

                 }
             }else if(t instanceof Integer){

                 JSONObject jsonRootObject = new JSONObject(BookJSON);
                 JSONArray genres = jsonRootObject.getJSONArray("genres");
                 for (int i=0;i<genres.length();i++){
                     JSONObject obj = genres.getJSONObject(i);
                     String name = obj.getString("name");
                     int id = obj.getInt("id");
                     data.add((T) new GenreLandData(name,id));

                 }
             }else if(t instanceof Double){

                 JSONObject jsonRootObject = new JSONObject(BookJSON);
                 String movie_backdrop_path = "https://image.tmdb.org/t/p/w500" + jsonRootObject.getString("backdrop_path");
                 String movie_title = jsonRootObject.getString("title");
                 String movie_tagline = jsonRootObject.getString("tagline");
                 JSONArray genre = jsonRootObject.getJSONArray("genres");
                 String movie_genre = "";
                 for (int i=0;i<genre.length();i++){
                     JSONObject obj = genre.getJSONObject(i);
                     movie_genre += obj.getString("name")+"<br/>";
                 }
                 String movie_original_lang = jsonRootObject.getString("original_language");
                 String movie_popularity = String.valueOf(jsonRootObject.getInt("popularity"));
                 String movie_release_date = jsonRootObject.getString("release_date");
                 String movie_runtime = jsonRootObject.getString("runtime");
                 String[] movie_crew = new String[50];
                 String[] movie_cast = new String[50];
                 // cast_and_char add here
                 JSONObject credits = jsonRootObject.getJSONObject("credits");
                 JSONArray crew = credits.getJSONArray("crew");

                 for(int i=0;i<crew.length() && i<50;i++){
                     JSONObject obj = crew.getJSONObject(i);
                     if(obj.getString("job").equals("Director")) {
                         movie_crew[i] = obj.getString("job") + "!!!" + obj.getString("name") + "!!!" + obj.getString("id") + "!!!" + obj.getString("profile_path") + "\n";
                     }else{ continue; }
                 }
                 JSONArray cast = credits.getJSONArray("cast");
                 for (int i=0;i<cast.length() && i<50;i++){
                     JSONObject obj = cast.getJSONObject(i);
                     movie_cast[i] = obj.getString("character") + "!!!" + obj.getString("name") + "!!!" + obj.getString("id") + "!!!" + obj.getString("profile_path") + "\n";
                 }
                 String[] C= new String[movie_cast.length+movie_crew.length];
                 System.arraycopy(movie_crew, 0, C, 0, movie_crew.length);
                 System.arraycopy(movie_cast, 0, C, movie_cast.length, movie_crew.length);

                 String movie_overview = jsonRootObject.getString("overview");
                 String movie_homepage = jsonRootObject.getString("homepage");
                 String movie_imdb = "https://www.imdb.com/title/" + jsonRootObject.getString("imdb_id");
                 data.add((T) new MovieDetailsData(movie_backdrop_path,movie_title,movie_tagline,movie_genre,movie_original_lang,
                         movie_popularity,movie_release_date,movie_runtime,C,movie_overview,movie_homepage,movie_imdb));


             }else if(t instanceof TextView){

                 JSONObject jsonRootObject = new JSONObject(BookJSON);
                 String pperson_birthday = jsonRootObject.getString("birthday");
                 String pperson_department = jsonRootObject.getString("known_for_department");
                 String pperson_name = jsonRootObject.getString("name");
                 int gender = jsonRootObject.getInt("gender");
                 String pperson_gender;
                 if(gender == 2){
                     pperson_gender = "Male";
                 }else{
                     pperson_gender = "Female";
                 }
                 String pperson_biography = jsonRootObject.getString("biography");
                 String pperson_pob = jsonRootObject.getString("place_of_birth");
                 String pperson_image_path = "https://image.tmdb.org/t/p/w500" + jsonRootObject.getString("profile_path");
                 String pperson_imdb_id = "https://www.imdb.com/name/" + jsonRootObject.getString("imdb_id");

                 data.add((T) new PersonFullData(pperson_birthday,pperson_department,pperson_name,pperson_gender
                 ,pperson_biography,pperson_pob,pperson_image_path,pperson_imdb_id));

             }else if(t instanceof RecyclerView){
                 JSONObject jsonRootObject = new JSONObject(BookJSON);
                 JSONArray results = jsonRootObject.getJSONArray("results");
                 for (int i = 0; i < results.length(); i++) {

                     JSONObject obj = results.getJSONObject(i);
                     String profile_path = "https://image.tmdb.org/t/p/w500" + obj.getString("profile_path");
                     int id = obj.getInt("id");
                     String name = obj.getString("name");
                     data.add((T) new PersonListData(profile_path,id,name));

                 }


             }

            } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
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
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
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