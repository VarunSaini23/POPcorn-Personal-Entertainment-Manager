package com.example.android.popcorn.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.popcorn.GenreJSONParser;
import com.example.android.popcorn.GenreLangActivity;
import com.example.android.popcorn.LoginActivity;
import com.example.android.popcorn.MovieListActivity;
import com.example.android.popcorn.R;
import com.example.android.popcorn.RecyclerItemClickListener;
import com.example.android.popcorn.SearchActivity;
import com.example.android.popcorn.adapters.GenreLangRecyclerVIewAdapter;
import com.example.android.popcorn.models.GenreLandData;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    FirebaseUser mFirebaseUser;
    FirebaseAuth mFirebaseAuth;
    List<AuthUI.IdpConfig> providers;
    AVLoadingIndicatorView avi;
    final static public int RC_SIGN_IN = 1;
    String name = "Anonynmous";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu);

//        providers = Arrays.asList(
//                new AuthUI.IdpConfig.EmailBuilder().build(),
//                new AuthUI.IdpConfig.GoogleBuilder().build());

        if(mFirebaseUser==null){
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
//                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);}else{
            name = mFirebaseUser.getDisplayName();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            name = user.getDisplayName();
            String url = "https://api.themoviedb.org/3/discover/movie?api_key=7da1ead276c45a0e2abd18144be5ea12&with_genres=878";
            Intent i = new Intent(MainActivity.this,MovieListActivity.class);
            i.putExtra("URL",url);
            startActivity(i);

        }


        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                if (menuItem.getTitle().equals("Search")){
                    Intent i = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(i);
                }else if(menuItem.getTitle().equals("Genre")){
                    Intent i = new Intent(MainActivity.this, GenreLangActivity.class);
                    i.putExtra("url","genre");
                    startActivity(i);
//                }else if(menuItem.getTitle().equals("Language")){
//                    Intent i = new Intent(MainActivity.this,GenreLangActivity.class);
//                    i.putExtra("url","lang");
//                    startActivity(i);
                }else if(menuItem.getTitle().equals("Logout")){
                    AuthUI.getInstance()
                            .signOut(MainActivity.this)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        finishAndRemoveTask();
                                    }
                                }
                            });
                }else if(menuItem.getTitle().equals("About Us")) {
                    Intent i = new Intent(MainActivity.this, AboutUsActivity.class);
                    startActivity(i);
                }

                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);


            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                name = user.getDisplayName();
                String url = "https://api.themoviedb.org/3/discover/movie?api_key=7da1ead276c45a0e2abd18144be5ea12&with_genres=878";
                Intent i = new Intent(MainActivity.this,MovieListActivity.class);
                i.putExtra("URL",url);
                startActivity(i);
                // ...
            } else {
                finish();
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }

    }



}
