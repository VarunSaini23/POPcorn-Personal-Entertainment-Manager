package com.example.android.popcorn;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PersonRecyclerVIewAdapter extends RecyclerView.Adapter<PersonRecyclerVIewAdapter.PersonRecyclerVIewHolder>{

    public ArrayList<PersonListData> s;
    AssetManager assetManager;
    Context context;

    public PersonRecyclerVIewAdapter(ArrayList<PersonListData> s){
        this.s = s;
    }

    @NonNull
    @Override
    public PersonRecyclerVIewAdapter.PersonRecyclerVIewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.card_2_column_list,viewGroup,false);

        assetManager = viewGroup.getContext().getAssets();
        context = viewGroup.getContext();
        return new PersonRecyclerVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonRecyclerVIewHolder recyclerVIewHolder, int i) {

        String aa = s.get(i).name;
        String bb = s.get(i).profile_path;

        recyclerVIewHolder.txtTitle.setText(aa);
        Log.d("image", "onBindViewHolder: "+ bb);
        if(!bb.equals("https://image.tmdb.org/t/p/w500null")){
        Glide.with(context).load(bb).into(recyclerVIewHolder.imgIcon);}
        else {
            recyclerVIewHolder.imgIcon.setImageResource(R.drawable.no_photo);
        }










    }

    @Override
    public int getItemCount() {
        return s.size();

    }


    public class PersonRecyclerVIewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView txtTitle;

        public PersonRecyclerVIewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtTitle = itemView.findViewById(R.id.txtTitle);

        }
    }
}
