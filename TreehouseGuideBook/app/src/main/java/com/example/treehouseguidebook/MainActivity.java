package com.example.treehouseguidebook;

import android.content.ComponentName;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.treehouseguidebook.fragments.GuideFragment;
import com.example.treehouseguidebook.fragments.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // navigation bar listener for bottom nav bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = new Fragment();
                // Removed switch case, deprecated since API 14
                if (menuItem.getItemId() == R.id.opGuide){
                    fragment = new GuideFragment();
                    Toast.makeText(MainActivity.this, "Clicked on Guide", Toast.LENGTH_SHORT).show();
                } else if (menuItem.getItemId() == R.id.opBell){
                    fragment = new NotificationsFragment();
                    Toast.makeText(MainActivity.this, "Clicked on Notifications Bell", Toast.LENGTH_SHORT).show();
                } else if (menuItem.getItemId() == R.id.opChat){
//                    fragment = new GuideFragment();
                    Toast.makeText(MainActivity.this, "Clicked on Chat", Toast.LENGTH_SHORT).show();
                } else if (menuItem.getItemId() == R.id.opBm) {
//                    fragment = new GuideFragment();
                    Toast.makeText(MainActivity.this, "Clicked on Bookmarks", Toast.LENGTH_SHORT).show();
                }

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.opGuide);

    }






}
