package com.example.android.popcorn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import iammert.com.view.scalinglib.ScalingLayout;
import iammert.com.view.scalinglib.ScalingLayoutListener;
import iammert.com.view.scalinglib.State;

public class SearchActivity extends AppCompatActivity {

    EditText editTextSearch,editTextSearchActor;
    SharedPreferences sh;
    ArrayList<String> arrayOfSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearchActor = findViewById(R.id.editTextSearch1);

        sh = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        arrayOfSearch = new ArrayList<>();
        Map<String, ?> allEntries = sh.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            arrayOfSearch.add(entry.getValue().toString());
            Log.d("asas", "onCreate: "+entry.getValue().toString() + "||");
        }



        final LinearLayout rootLayout = findViewById(R.id.rootLayout);
        final TextView textViewSearch = findViewById(R.id.textViewSearch);
        final RelativeLayout searchLayout = findViewById(R.id.searchLayout);
        final ScalingLayout scalingLayout = findViewById(R.id.scalingLayout);

        RecyclerView recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new SearchRecyclerVIewAdapter(arrayOfSearch));

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
        sh = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        Calendar c = Calendar.getInstance();
        int key = (int) c.getTimeInMillis();
        editor.putString( String.valueOf(key),"Movie" + "#" + search_query);
        editor.apply();
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
        SharedPreferences.Editor editor = sh.edit();
        Calendar c = Calendar.getInstance();
        int key = (int) c.getTimeInMillis();
        editor.putString(String.valueOf(key),"Actor" + "#" + search_query);
        editor.apply();
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

    public class SearchRecyclerVIewAdapter extends RecyclerView.Adapter<SearchRecyclerVIewAdapter.RecyclerVIewHolder> {

        ArrayList<String> s;
        AssetManager assetManager;

        public SearchRecyclerVIewAdapter(ArrayList<String> s) {
            this.s = s;
        }

        @NonNull
        @Override
        public SearchRecyclerVIewAdapter.RecyclerVIewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View view = layoutInflater.inflate(R.layout.search_card_list, viewGroup, false);

            assetManager = viewGroup.getContext().getAssets();
            return new SearchRecyclerVIewAdapter.RecyclerVIewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchRecyclerVIewAdapter.RecyclerVIewHolder recyclerVIewHolder, int i) {

            String title = s.get(i);
            String[] array = s.get(i).split("#");
            if(array[0].equals("Movie")) {
                recyclerVIewHolder.searchTextView.setText((i + 1) + " . " + array[1]);
                recyclerVIewHolder.cardColor.setBackgroundResource(R.drawable.list_grad_purple);
            }else{
                recyclerVIewHolder.cardColor.setBackgroundResource(R.drawable.list_grad_pink);
                recyclerVIewHolder.searchTextView.setText((i + 1) + " . " + array[1]);
            }

            Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/Montserrat.ttf");
            recyclerVIewHolder.searchTextView.setTypeface(typeface);

            recyclerVIewHolder.fullCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return s.size();

        }


        public class RecyclerVIewHolder extends RecyclerView.ViewHolder {
            TextView searchTextView;
            CardView fullCard;
            LinearLayout cardColor;

            public RecyclerVIewHolder(@NonNull View itemView) {
                super(itemView);
                searchTextView = itemView.findViewById(R.id.searchTextView);
                fullCard = itemView.findViewById(R.id.fullCard);
                cardColor = itemView.findViewById(R.id.cardColor);

            }
        }
    }
}
