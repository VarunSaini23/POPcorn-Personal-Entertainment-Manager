package com.example.android.popcorn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.android.popcorn.adapters.GenreLangRecyclerVIewAdapter;
import com.example.android.popcorn.models.GenreLandData;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class GenreLangActivity extends AppCompatActivity {


    ArrayList<GenreLandData> arrayList;
    String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=7da1ead276c45a0e2abd18144be5ea12&language=en-US";


    RecyclerView recyclerView;
    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_lang);
        Bundle extras = getIntent().getExtras();
        String base = extras.getString("url");
        if(base=="genre"){
            url = "https://api.themoviedb.org/3/genre/movie/list?api_key=7da1ead276c45a0e2abd18144be5ea12&language=en-US";
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu);


        recyclerView = findViewById(R.id.genreRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<GenreLandData>();

        avi = findViewById(R.id.avi);
        avi.setIndicator((new ProgressIndicatorSelector().ProgressIndicatorSelector()));

        GenreLangAsyncTask genreLangAsyncTask = new GenreLangAsyncTask();
        genreLangAsyncTask.execute(url);

    }

    private class GenreLangAsyncTask extends AsyncTask<String,Void,List<GenreLandData>> {
        @SuppressLint("WrongThread")
        @Override
        protected List<GenreLandData> doInBackground(String... urls) {
            if(urls.length<1 || urls[0]==null){
                return null;
            }
            avi.smoothToShow();
            GenreJSONParser<GenreLandData> genreJSONParser = new GenreJSONParser<>();

            List<GenreLandData> list = genreJSONParser.fetchBookData(urls[0],123);


            return list;
        }


        @Override
        protected void onPostExecute(final List<GenreLandData> data) {
            Log.e("hello1","alright1");
            if (data != null && !data.isEmpty()) {

                final List<GenreLandData> newList = new ArrayList<GenreLandData>(data);
                avi.smoothToHide();

                recyclerView.setAdapter(new GenreLangRecyclerVIewAdapter((ArrayList<GenreLandData>) data));
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(GenreLangActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        String url = "https://api.themoviedb.org/3/discover/movie?api_key=7da1ead276c45a0e2abd18144be5ea12&with_genres=";
                        String genre_url = String.valueOf(data.get(position).id);
                        url += genre_url;
                        Intent i = new Intent(GenreLangActivity.this,MovieListActivity.class);
                        i.putExtra("URL",url);
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                }));
                Log.e("hello3","alright3");
            }
            Log.e("hello2","alright2");
        }
    }
}
