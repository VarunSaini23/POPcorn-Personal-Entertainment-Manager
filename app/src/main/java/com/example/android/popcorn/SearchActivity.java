package com.example.android.popcorn;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import iammert.com.view.scalinglib.ScalingLayout;
import iammert.com.view.scalinglib.ScalingLayoutListener;
import iammert.com.view.scalinglib.State;

public class SearchActivity extends AppCompatActivity {

    EditText editTextSearch,editTextSearchActor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearchActor = findViewById(R.id.editTextSearch1);

        final LinearLayout rootLayout = findViewById(R.id.rootLayout);
        final TextView textViewSearch = findViewById(R.id.textViewSearch);
        final RelativeLayout searchLayout = findViewById(R.id.searchLayout);
        final ScalingLayout scalingLayout = findViewById(R.id.scalingLayout);

        final TextView textViewSearch1 = findViewById(R.id.textViewSearch1);
        final RelativeLayout searchLayout1 = findViewById(R.id.searchLayout1);
        final ScalingLayout scalingLayout1 = findViewById(R.id.scalingLayout1);

        new SearchBox(textViewSearch,searchLayout,scalingLayout,rootLayout);
        new SearchBox(textViewSearch1,searchLayout1,scalingLayout1,rootLayout);

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scalingLayout.getState()== State.EXPANDED || scalingLayout1.getState()==State.EXPANDED){
                    scalingLayout.collapse();
                    scalingLayout1.collapse();
                }
            }
        });

    }

    public void arrow_search_movie(View view){

        String url = "https://api.themoviedb.org/3/search/movie?api_key=7da1ead276c45a0e2abd18144be5ea12&query=";
        String search_query = editTextSearch.getText().toString();
        String final_search = search_query.replaceAll(" ","+");
        url += final_search;
        Log.e("wew",final_search);
        if(url.equals("https://api.themoviedb.org/3/search/movie?api_key=7da1ead276c45a0e2abd18144be5ea12&query=")){
            Toast.makeText(this, "Search can't be empty", Toast.LENGTH_SHORT).show();
        }else {
            Intent i = new Intent(SearchActivity.this, MovieListActivity.class);
            i.putExtra("URL", url);
            startActivity(i);
        }


    }

    public void arrow_search_actor(View view){

        String url = "https://api.themoviedb.org/3/search/person?api_key=7da1ead276c45a0e2abd18144be5ea12&query=";
        String search_query = editTextSearchActor.getText().toString();
        String final_search = search_query.replaceAll(" ","+");
        url += final_search;
        Log.e("wew",final_search);
        if(url.equals("https://api.themoviedb.org/3/search/person?api_key=7da1ead276c45a0e2abd18144be5ea12&query=")){
            Toast.makeText(this, "Search can't be empty", Toast.LENGTH_SHORT).show();
        }else {
            Intent i = new Intent(SearchActivity.this, PersonListActivity.class);
            i.putExtra("URL", url);
            startActivity(i);
        }


    }

}
