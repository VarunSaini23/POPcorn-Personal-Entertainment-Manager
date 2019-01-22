package com.example.android.popcorn;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class CastCrewFullInfoActivity extends AppCompatActivity{

    ImageView person_image;
    Button button_imdb;
    TextView person_name,person_gender,person_birthday,person_age,person_department,person_biography;
    String person_url = "https://api.themoviedb.org/3/person/";
    String person_imdb = "";
    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_crew_full_info);

        person_name = findViewById(R.id.person_name);
        person_gender = findViewById(R.id.person_gender);
        person_birthday = findViewById(R.id.person_birthday);
        person_age = findViewById(R.id.person_age);
        person_department = findViewById(R.id.person_department);
        person_biography = findViewById(R.id.person_biography);
        person_image = findViewById(R.id.person_image);
        button_imdb = findViewById(R.id.button_imdb);

        Bundle extras = getIntent().getExtras();
        person_url += extras.getString("PERSON_ID");
        person_url += "?api_key=7da1ead276c45a0e2abd18144be5ea12&language=en-US";

        avi = findViewById(R.id.avi);
        avi.setIndicator((new ProgressIndicatorSelector().ProgressIndicatorSelector()));

        PersonDetailsAsyncTask personDetailsAsyncTask = new PersonDetailsAsyncTask();
        personDetailsAsyncTask.execute(person_url);



    }

    public void home_imdb_page_url(String imdbpage){
        this.person_imdb = imdbpage;
    }

    public void imdb(View view){
        Intent i = new Intent(CastCrewFullInfoActivity.this,CoomonWebViewActivity.class);
        i.putExtra("uurrll",person_imdb);
        startActivity(i);
    }


    private class PersonDetailsAsyncTask extends AsyncTask<String,Void,List<PersonFullData>> {
        @Override
        protected List<PersonFullData> doInBackground(String... urls) {
            if(urls.length<1 || urls[0]==null){
                return null;
            }
            avi.smoothToShow();

            GenreJSONParser<PersonFullData> genreJSONParser = new GenreJSONParser<>();

            ArrayList<PersonFullData> list;

            list = (ArrayList<PersonFullData>) genreJSONParser.fetchBookData(urls[0],person_name);

            if(list.isEmpty()){
                Log.d("tag", "doInBackground:Empty list");
            }

            return list;

        }
        ArrayList<CastCrewData> castCrewData;

        @Override
        protected void onPostExecute(List<PersonFullData> data) {
            Log.e("hello1","alright1");
            if (data != null && !data.isEmpty()) {
                avi.smoothToHide();
                Log.d("Image_url", "onPostExecute: "+data.get(0).pperson_image_path);
                if(!(data.get(0).pperson_image_path.equals("https://image.tmdb.org/t/p/w500null"))) {
                    Glide.with(CastCrewFullInfoActivity.this).load(data.get(0).pperson_image_path).into(person_image);
                }else{
                    person_image.setImageResource(R.drawable.no_photo);
                }
                person_name.setText(data.get(0).pperson_name);
                person_gender.setText(Html.fromHtml("<b>Gender : </b>" + data.get(0).pperson_gender));
                person_birthday.setText(Html.fromHtml("<b>Birthday : </b>" + data.get(0).pperson_birthday));
                person_department.setText(Html.fromHtml("<b>Department : </b>" + data.get(0).pperson_department));
                person_biography.setText(Html.fromHtml("<b>Biography : </b><br/>" + data.get(0).pperson_biography ));

                person_imdb += data.get(0).pperson_imdb_id;
                CastCrewFullInfoActivity.this.home_imdb_page_url(person_imdb);


                Log.e("hello3","alright3");
            }


            Log.e("hello2","alright2");

        }
    }
}
