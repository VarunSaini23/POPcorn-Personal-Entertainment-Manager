package com.example.android.popcorn.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.popcorn.R;
import com.example.android.popcorn.models.SampleData;

import java.util.ArrayList;

public class RecyclerVIewAdapter extends RecyclerView.Adapter<RecyclerVIewAdapter.RecyclerVIewHolder>{

    public ArrayList<SampleData> s ;
    AssetManager assetManager;
    Context context;
    public RecyclerVIewAdapter(ArrayList<SampleData> s){
        this.s = s;
    }

    @NonNull
    @Override
    public RecyclerVIewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.card_list,viewGroup,false);

        assetManager = viewGroup.getContext().getAssets();
        context = viewGroup.getContext();
        return new RecyclerVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerVIewHolder recyclerVIewHolder, int i) {

        String aa = s.get(i).title;
            double bb = s.get(i).popularity;
            double cc = s.get(i).rate;
            String dd = s.get(i).release_date;
            String ee = s.get(i).image;

        recyclerVIewHolder.txtTitle.setText(aa);
        recyclerVIewHolder.num_popularity.setText(Html.fromHtml("<b>"+"Popularity "+"</b>"+String.valueOf(bb)));
        recyclerVIewHolder.num_vote.setText(Html.fromHtml("<b>"+"Vote "+"</b>"+String.valueOf(cc)));
        recyclerVIewHolder.release_date.setText(Html.fromHtml("<b>"+"Release Date "+"</b>"+"<br>"+String.valueOf(dd)));
//      recyclerVIewHolder.imgIcon.setImageURI(Uri.parse(ee));
        Glide.with(context).load(ee).into(recyclerVIewHolder.imgIcon);



        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/Unlock.ttf");
        recyclerVIewHolder.txtTitle.setTypeface(typeface);
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/Montserrat.ttf");
        recyclerVIewHolder.num_popularity.setTypeface(typeface1);
        recyclerVIewHolder.num_vote.setTypeface(typeface1);
        recyclerVIewHolder.release_date.setTypeface(typeface1);







    }

    @Override
    public int getItemCount() {
        return s.size();

    }


    public class RecyclerVIewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView txtTitle;
        TextView num_popularity;
        TextView num_vote;
        TextView release_date;

        public RecyclerVIewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            num_popularity = itemView.findViewById(R.id.num_popularity);
            num_vote = itemView.findViewById(R.id.num_vote);
            release_date = itemView.findViewById(R.id.release_date);

        }
    }
}
