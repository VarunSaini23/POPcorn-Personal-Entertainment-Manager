package com.example.android.popcorn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.popcorn.models.CastCrewData;
import com.example.android.popcorn.activity.CoomonWebViewActivity;
import com.example.android.popcorn.models.MovieDetailsData;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import iammert.com.view.scalinglib.ScalingLayout;
import iammert.com.view.scalinglib.ScalingLayoutListener;

public class MovieDetailActivity extends AppCompatActivity {

    String movie_url = "http://api.themoviedb.org/3/movie/";

    ImageView backdrop_path;
    TextView movie_title,tagline,genres,lang,popularity,release_date_movie,runtime,cast;
    TextView overview;
    Toolbar toolbar;
    ListView listView;
    AVLoadingIndicatorView avi;
    String movie_homepage = "",movie_imdb = "";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        backdrop_path = findViewById(R.id.backdrop_path);
        movie_title = findViewById(R.id.movie_title);
        tagline = findViewById(R.id.tagline);
        genres = findViewById(R.id.genres);
        lang = findViewById(R.id.lang);
        popularity = findViewById(R.id.popularity);
        release_date_movie = findViewById(R.id.release_date_movie);
        runtime = findViewById(R.id.runtime);
        cast = findViewById(R.id.cast);
        overview = findViewById(R.id.overview);
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listView);

        listView.setBackgroundColor(0xfffffff);
        listView.setBackgroundColor(R.color.colorAccent);


        avi = findViewById(R.id.avi);
        avi.setIndicator((new ProgressIndicatorSelector().ProgressIndicatorSelector()));


        int[] textviews_fontchange = {R.id.backdrop_path,R.id.movie_title};
        TextView[] textViews = {tagline,genres,lang,popularity,release_date_movie,runtime};

        Bundle extras = getIntent().getExtras();
        movie_url += extras.getString("movie_url");
        movie_url += "?api_key=7da1ead276c45a0e2abd18144be5ea12&append_to_response=credits";

        Log.d("M U", "onCreate: "+movie_url);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MovieDetailsAsyncTask movieDetailsAsyncTask = new MovieDetailsAsyncTask();
        movieDetailsAsyncTask.execute(movie_url);

        Typeface typeface_title = Typeface.createFromAsset(getAssets(),"fonts/Bungee.ttf");
        movie_title.setTypeface(typeface_title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat.ttf");


        for(int i=0;i<textViews.length;i++){
            TextView textView = textViews[i];
            textView.setTypeface(typeface);
        }

        Typeface typeface_overview = Typeface.createFromAsset(getAssets(),"fonts/Oxygen.ttf");
        overview.setTypeface(typeface_overview);





        ScalingLayout scalingLayout = findViewById(R.id.scalingLayout);

        scalingLayout.setListener(new ScalingLayoutListener() {
            @Override
            public void onCollapsed() { }

            @Override
            public void onExpanded() { }

            @Override
            public void onProgress(float progress) { }
        });


    }

    public void home_imdb_page_url(String homepage,String imdbpage){
        this.movie_homepage = homepage;
        this.movie_imdb = imdbpage;
    }

    public void homepage(View view){

        Log.d("home", "homepage: "+movie_homepage);
        if(TextUtils.isEmpty(movie_homepage) || movie_homepage.equals("null") ){
            Toast.makeText(this, "Sorry , No homepage", Toast.LENGTH_SHORT).show();
        }else{
        Intent i = new Intent(MovieDetailActivity.this, CoomonWebViewActivity.class);
        i.putExtra("uurrll",movie_homepage);
        startActivity(i);}
    }

    public void imdb(View view){
        Intent i = new Intent(MovieDetailActivity.this,CoomonWebViewActivity.class);
        i.putExtra("uurrll",movie_imdb);
        startActivity(i);
    }

    private class MovieDetailsAsyncTask extends AsyncTask<String,Void,List<MovieDetailsData>> {
        @Override
        protected List<MovieDetailsData> doInBackground(String... urls) {
            if(urls.length<1 || urls[0]==null){
                return null;
            }
            avi.smoothToShow();

            GenreJSONParser<MovieDetailsData> genreJSONParser = new GenreJSONParser<>();

            ArrayList<MovieDetailsData> list;

            list = (ArrayList<MovieDetailsData>) genreJSONParser.fetchBookData(urls[0],1.11);

            if(list.isEmpty()){
                Log.d("tag", "doInBackground:Empty list");
            }

            return list;

        }
        ArrayList<CastCrewData> castCrewData;

        @Override
        protected void onPostExecute(List<MovieDetailsData> data) {
            Log.e("hello1","alright1");
            if (data != null && !data.isEmpty()) {
                avi.smoothToHide();

                Glide.with(MovieDetailActivity.this).load(data.get(0).movie_backdrop_path).into(backdrop_path);
                movie_title.setText(data.get(0).movie_title);
                tagline.setText(Html.fromHtml("<b>Tagline : </b>" + data.get(0).movie_tagline));
                genres.setText(Html.fromHtml("<b>Genres : </b><br/>" + data.get(0).movie_genre));
                lang.setText(Html.fromHtml("<b>Original language : </b>" + data.get(0).movie_original_lang));
                popularity.setText(Html.fromHtml("<b>Populaity : </b>" + data.get(0).movie_popularity));
                release_date_movie.setText(Html.fromHtml("<b>Release Date : </b>" + data.get(0).movie_release_date));
                runtime.setText(Html.fromHtml("<b>Movie Runtime : </b>" + data.get(0).movie_runtime + "mins."));

                int countNotNull=0;
                for (int i=0;i<data.get(0).movie_char_and_cast.length;i++){
                    if(!TextUtils.isEmpty(data.get(0).movie_char_and_cast[i])){
                        countNotNull++;}
                }

                String cast_crew[] = new String[countNotNull];
                int j=0;
                castCrewData = new ArrayList<>();
                for (int i=0;i<data.get(0).movie_char_and_cast.length;i++){
                    if(!TextUtils.isEmpty(data.get(0).movie_char_and_cast[i])){
                    String[] allData = data.get(0).movie_char_and_cast[i].split("!!!");
                    cast_crew[j] = allData[0] + " - " + allData[1];
                    castCrewData.add(new CastCrewData(allData[0],allData[1],allData[2],allData[3]));

                    j++;}
                }

                overview.setText(Html.fromHtml("<b>Overview : </b>\n" + data.get(0).movie_overview));

                toolbar.setTitle(data.get(0).movie_title);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MovieDetailActivity.this,R.layout.cast_crew_list,cast_crew);
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(MovieDetailActivity.this, "Clicked on "+String.valueOf(castCrewData.get(position).person_name), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MovieDetailActivity.this,CastCrewFullInfoActivity.class);
                        i.putExtra("PERSON_ID",String.valueOf(castCrewData.get(position).person_id));
                        startActivity(i);
                    }
                });

                movie_homepage += data.get(0).movie_homepage;
                movie_imdb += data.get(0).movie_imdb;
                MovieDetailActivity.this.home_imdb_page_url(movie_homepage,movie_imdb);

                Log.e("hello3","alright3");
            }


            Log.e("hello2","alright2");

        }
    }



}
