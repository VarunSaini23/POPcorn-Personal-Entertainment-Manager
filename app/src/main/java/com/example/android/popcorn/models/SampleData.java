package com.example.android.popcorn.models;

public class SampleData {

    public String title;
    public double popularity;
    public double rate;
    public String release_date;
    public String image;
    public int id;

    public SampleData(String title, double popularity, double rate, String release_date, String image,int id){
        this.title = title;
        this.popularity = popularity;
        this.rate = rate;
        this.release_date = release_date;
        this.image = image;
        this.id = id;
    }

}
