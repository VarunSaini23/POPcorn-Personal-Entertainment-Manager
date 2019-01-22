package com.example.android.popcorn;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GenreLangRecyclerVIewAdapter extends RecyclerView.Adapter<GenreLangRecyclerVIewAdapter.RecyclerVIewHolder>{

    ArrayList<GenreLandData> s;
    AssetManager assetManager;

    public GenreLangRecyclerVIewAdapter(ArrayList<GenreLandData> s){
        this.s = s;
    }

    @NonNull
    @Override
    public RecyclerVIewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.genre_card_list,viewGroup,false);

        assetManager = viewGroup.getContext().getAssets();
        return new RecyclerVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerVIewHolder recyclerVIewHolder, int i) {

        String title = s.get(i).title;
        int id = s.get(i).id;
        recyclerVIewHolder.sno.setText(String.valueOf(i+1));
        recyclerVIewHolder.txtTitle.setText(title);


        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/Montserrat.ttf");
        recyclerVIewHolder.txtTitle.setTypeface(typeface);







    }

    @Override
    public int getItemCount() {
        return s.size();

    }


    public class RecyclerVIewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView sno;


        public RecyclerVIewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            sno = itemView.findViewById(R.id.sno);


        }
    }
}
