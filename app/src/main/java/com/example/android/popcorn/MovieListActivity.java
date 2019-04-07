package com.example.android.popcorn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.popcorn.adapters.RecyclerVIewAdapter;
import com.example.android.popcorn.models.SampleData;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import iammert.com.view.scalinglib.ScalingLayout;
import iammert.com.view.scalinglib.ScalingLayoutListener;
import iammert.com.view.scalinglib.State;

public class MovieListActivity extends AppCompatActivity {

    ArrayList<SampleData> arrayList;
        //The key argument here must match that used in the other activity
    ArrayList<SampleData> newList;
    int selected = 1;
    AVLoadingIndicatorView avi;
    RelativeLayout sort_date;
    RelativeLayout sort_rate;
    RelativeLayout sort_pop;

    String url;
    RecyclerView recyclerView;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        Bundle extras = getIntent().getExtras();
        url = extras.getString("URL");

        avi = findViewById(R.id.avi);
        avi.setIndicator((new ProgressIndicatorSelector().ProgressIndicatorSelector()));



        sort_date = findViewById(R.id.sort_date);
        sort_rate = findViewById(R.id.sort_rate);
        sort_pop = findViewById(R.id.sort_pop);
        sort_pop.setBackgroundColor(R.color.colorPrimaryDark);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<>();
        MovieAsyncTask bookAsyncTask = new MovieAsyncTask();
        bookAsyncTask.execute(url);

        final ImageView fabIcon = findViewById(R.id.fabIcon);
        final LinearLayout filterLayout = findViewById(R.id.filterLayout);
        final ScalingLayout scalingLayout = findViewById(R.id.scalingLayout);
        if (url.contains("search/movie?") && (url.contains("&query="))){
            scalingLayout.setVisibility(View.GONE);
        }

        scalingLayout.setListener(new ScalingLayoutListener() {
            @Override
            public void onCollapsed() {
                ViewCompat.animate(fabIcon).alpha(1).setDuration(150).start();
                ViewCompat.animate(filterLayout).alpha(0).setDuration(150).setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        fabIcon.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        filterLayout.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).start();
            }

            @Override
            public void onExpanded() {
                ViewCompat.animate(fabIcon).alpha(0).setDuration(200).start();
                ViewCompat.animate(filterLayout).alpha(1).setDuration(200).setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        filterLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        fabIcon.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).start();
            }

            @Override
            public void onProgress(float progress) {
                if (progress > 0) {
                    fabIcon.setVisibility(View.INVISIBLE);
                }

                if(progress < 1){
                    filterLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        scalingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scalingLayout.getState() == State.COLLAPSED) {
                    scalingLayout.expand();
                }
            }
        });


        findViewById(R.id.rootLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scalingLayout.getState() == State.EXPANDED) {
                    scalingLayout.collapse();
                }
            }
        });



    }

    @SuppressLint("ResourceAsColor")
    public void sort_date(View view){


        arrayList.clear();

        sort_date.setBackgroundColor(R.color.colorPrimaryDark);
        sort_pop.setBackgroundColor(0x00000000);
        sort_rate.setBackgroundColor(0x00000000);

        String url_date = url + "&sort_by=primary_release_date.desc&primary_release_date.lte=2018-12-01";
        MovieAsyncTask bookAsyncTask = new MovieAsyncTask();
        bookAsyncTask.execute(url_date);
        Toast.makeText(this, "Clicked on SORT BY DATE", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ResourceAsColor")
    public void sort_rate(View view){
        arrayList.clear();

        sort_rate.setBackgroundColor(R.color.colorPrimaryDark);
        sort_date.setBackgroundColor(0x00000000);
        sort_pop.setBackgroundColor(0x00000000);

        String url_rate = url + "&sort_by=vote_average.desc&vote_average.lte=9.9&vote_count.gte=200";
        MovieAsyncTask bookAsyncTask = new MovieAsyncTask();
        bookAsyncTask.execute(url_rate);

        Toast.makeText(this, "Clicked on SORT BY RATE", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ResourceAsColor")
    public void sort_pop(View view){
        arrayList.clear();

        sort_pop.setBackgroundColor(R.color.colorPrimaryDark);
        sort_date.setBackgroundColor(0x00000000);
        sort_rate.setBackgroundColor(0x00000000);

        MovieAsyncTask bookAsyncTask = new MovieAsyncTask();
        bookAsyncTask.execute(url);
        Toast.makeText(this, "Clicked on SORT BY POPULARITY", Toast.LENGTH_SHORT).show();
    }


    private class MovieAsyncTask extends AsyncTask<String,Void,List<SampleData>> {
        @Override
        protected List<SampleData> doInBackground(String... urls) {
            if(urls.length<1 || urls[0]==null){
                return null;
            }
            avi.smoothToShow();

            GenreJSONParser<SampleData> genreJSONParser = new GenreJSONParser<>();

            ArrayList<SampleData> list;

            list = (ArrayList<SampleData>) genreJSONParser.fetchBookData(urls[0],"varun");

            if(list.isEmpty()){
                Log.d("tag", "doInBackground:Empty list");
            }

            return list;

        }



        @Override
        protected void onPostExecute(List<SampleData> data) {
            Log.e("hello1","alright1");
            if (data != null && !data.isEmpty()) {

                newList = new ArrayList<>(data);

                recyclerView.setAdapter(new RecyclerVIewAdapter((ArrayList<SampleData>) data));
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemViewCacheSize(20);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                avi.smoothToHide();
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MovieListActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        Intent i = new Intent(MovieListActivity.this,MovieDetailActivity.class);
                        i.putExtra("movie_url",String.valueOf(newList.get(position).id));
                        startActivity(i);
                        Toast.makeText(MovieListActivity.this, newList.get(position).title, Toast.LENGTH_SHORT).show();
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
