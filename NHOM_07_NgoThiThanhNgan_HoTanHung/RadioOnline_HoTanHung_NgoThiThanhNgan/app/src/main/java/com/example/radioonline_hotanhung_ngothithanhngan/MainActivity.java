package com.example.radioonline_hotanhung_ngothithanhngan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Radio Online");
        display(R.id.menu_home);
        bottomNavigationView = findViewById(R.id.menu_nv);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                display(item.getItemId());

                return true;
            }
        });
        FavDB favDB = new FavDB(getApplicationContext());
        favDB.createTable();
    }


    void display(int id){
        Fragment fragment = null;
        toolbar.getMenu().clear();
        switch (id){
            case R.id.menu_home:
                toolbar.setTitle("Radio Online");
                fragment = new HomeFragment();
                break;
            case R.id.menu_channels:
                toolbar.setTitle("Radio Online");
                fragment = new ChannelsFragment();
                break;
            case R.id.menu_favorite:
                toolbar.setTitle("Radio Online");
                fragment =new FavoriteFragment();
                break;
            case R.id.menu_language:
                toolbar.setTitle("Radio Online");
                fragment = new LanguageFragment();
                break;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);
        ft.commit();
    }
}