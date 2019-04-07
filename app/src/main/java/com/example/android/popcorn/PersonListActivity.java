package com.example.android.popcorn;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.popcorn.adapters.PersonRecyclerVIewAdapter;
import com.example.android.popcorn.models.PersonListData;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class PersonListActivity extends AppCompatActivity {

    String url;
    RecyclerView recyclerView;
    ArrayList<PersonListData> arrayList;
    ArrayList<PersonListData> newList;
    AVLoadingIndicatorView avi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);

        Bundle extras = getIntent().getExtras();
        url = extras.getString("URL");

        avi = findViewById(R.id.avi);
        avi.setIndicator((new ProgressIndicatorSelector().ProgressIndicatorSelector()));

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutManager);

        arrayList = new ArrayList<>();

        PersonListAsyncTask personListAsyncTask = new PersonListAsyncTask();
        personListAsyncTask.execute(url);
    }


    private class PersonListAsyncTask extends AsyncTask<String,Void,List<PersonListData>> {
        @Override
        protected List<PersonListData> doInBackground(String... urls) {
            if(urls.length<1 || urls[0]==null){
                return null;
            }

            avi.smoothToShow();

            GenreJSONParser<PersonListData> genreJSONParser = new GenreJSONParser<>();

            ArrayList<PersonListData> list;

            list = (ArrayList<PersonListData>) genreJSONParser.fetchBookData(urls[0],recyclerView);

            if(list.isEmpty()){
                Log.d("tag", "doInBackground:Empty list");
            }

            return list;

        }



        @Override
        protected void onPostExecute(List<PersonListData> data) {
            Log.e("hello1","alright1");
            if (data != null && !data.isEmpty()) {

                newList = new ArrayList<>(data);

                avi.smoothToHide();

                recyclerView.setAdapter(new PersonRecyclerVIewAdapter((ArrayList<PersonListData>) data));
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemViewCacheSize(20);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(PersonListActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        Intent i = new Intent(PersonListActivity.this,CastCrewFullInfoActivity.class);
                        i.putExtra("PERSON_ID",String.valueOf(newList.get(position).id));
                        startActivity(i);
                        Toast.makeText(PersonListActivity.this, newList.get(position).name, Toast.LENGTH_SHORT).show();
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
