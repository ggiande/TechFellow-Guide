package com.example.treehouseguidebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.opGuide:
                        //fragment = fragment1;
                        break;
                    case R.id.opBell:
                        //fragment = fragment2;
                        break;
                    case R.id.opChat:
                        //fragment = fragment2;
                        break;
                    case R.id.opList:
                        //fragment = fragment2;
                        break;
                    case R.id.opBm:
                    default:
                        //fragment = fragment3;
                        break;
                }
                return true;            }
        });


    }





}
