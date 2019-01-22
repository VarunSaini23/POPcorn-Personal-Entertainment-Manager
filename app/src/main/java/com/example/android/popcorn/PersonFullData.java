package com.example.android.popcorn;

public class PersonFullData {
    public String pperson_birthday;
    public String pperson_department;
    public String pperson_name;
    public String pperson_gender;
    public String pperson_biography;
    public String pperson_pob;
    public String pperson_image_path;
    public String pperson_imdb_id;

    public PersonFullData(String pperson_birthday,String pperson_department,String pperson_name,String pperson_gender,
                          String pperson_biography,String pperson_pob,String pperson_image_path,String pperson_imdb_id){
        this.pperson_birthday = pperson_birthday;
        this.pperson_department = pperson_department;
        this.pperson_name = pperson_name;
        this.pperson_gender = pperson_gender;
        this.pperson_biography = pperson_biography;
        this.pperson_pob = pperson_pob;
        this.pperson_image_path = pperson_image_path;
        this.pperson_imdb_id = pperson_imdb_id;
    }



}
