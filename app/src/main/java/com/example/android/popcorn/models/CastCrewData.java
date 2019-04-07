package com.example.android.popcorn.models;

public class CastCrewData {

    public String job_or_char;
    public String person_name;
    public String person_id;
    public String profile_path;

    public CastCrewData(String job_or_char,String person_name,String person_id,String profile_path){
        this.job_or_char = job_or_char;
        this.person_name = person_name;
        this.person_id = person_id;
        this.profile_path = profile_path;
    }
}
