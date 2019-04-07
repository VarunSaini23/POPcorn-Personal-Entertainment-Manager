package com.example.android.popcorn.models;

public class MovieDetailsData {

    public String movie_backdrop_path;
    public String movie_title;
    public String movie_tagline;
    public String movie_genre;
    public String movie_original_lang;
    public String movie_popularity;
    public String movie_release_date;
    public String movie_runtime;
    public String[] movie_char_and_cast;
    public String movie_overview;
    public String movie_homepage;
    public String movie_imdb;

    public MovieDetailsData(String movie_backdrop_path,String movie_title,String movie_tagline,String movie_genre,String movie_original_lang,String movie_popularity,String movie_release_date,String movie_runtime,String[] movie_char_and_cast,String movie_overview,String movie_homepage,String movie_imdb){
        this.movie_backdrop_path = movie_backdrop_path;
        this.movie_title = movie_title;
        this.movie_tagline = movie_tagline;
        this.movie_genre = movie_genre;
        this.movie_original_lang = movie_original_lang;
        this.movie_popularity = movie_popularity;
        this.movie_release_date = movie_release_date;
        this.movie_runtime = movie_runtime;
        this.movie_char_and_cast = movie_char_and_cast;
        this.movie_overview = movie_overview;
        this.movie_homepage = movie_homepage;
        this.movie_imdb = movie_imdb;
    }

}
