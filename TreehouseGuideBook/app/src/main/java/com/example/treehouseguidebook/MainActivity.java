package com.example.treehouseguidebook;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.treehouseguidebook.fragments.GuideFragment;
import com.example.treehouseguidebook.fragments.NotificationsFragment;
import com.example.treehouseguidebook.fragments.SettingsFragment;
import com.example.treehouseguidebook.fragments.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private DatabaseReference ref;
    private FirebaseDatabase database;
    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sp;
    private String userId;
    private String userUni;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        database=FirebaseDatabase.getInstance();
        sp = getSharedPreferences("login", MODE_PRIVATE);
        userId = sp.getString("userId", " ");
        userUni = sp.getString("UserSchool", " ");
        Log.i("TAG", userUni);
        ref=database.getReference("schools").child(userUni);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d: snapshot.getChildren())
                {
                    for(DataSnapshot d1: d.getChildren()) {
                        Log.d("Login", d1.getKey());
                        if (userId.equals(d1.getKey())) {
//                        Toast.makeText(getApplicationContext(),"correct username",Toast.LENGTH_SHORT).show();
                            User u = d1.getValue(User.class);
                            Log.d("LoginPwd", u.getPwd());
                            Singleton.setExisting_user(u);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                }
                else if (menuItem.getItemId() == R.id.opBell){
                    fragment = new NotificationsFragment();
                    Toast.makeText(MainActivity.this, "Clicked on Notifications Bell", Toast.LENGTH_SHORT).show();
                }
                else if (menuItem.getItemId() == R.id.opSetting){
                    fragment = new SettingsFragment();
                    Toast.makeText(MainActivity.this, "Clicked on Settings", Toast.LENGTH_SHORT).show();
                }

                else if (menuItem.getItemId() == R.id.opChat){
                    fragment = new UsersFragment();
                    Toast.makeText(MainActivity.this, "Clicked on Chat", Toast.LENGTH_SHORT).show();
                }

                else if (menuItem.getItemId() == R.id.opBm) {
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
