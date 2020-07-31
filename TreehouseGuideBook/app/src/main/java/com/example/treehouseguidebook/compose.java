package com.example.treehouseguidebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.treehouseguidebook.fragments.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class compose extends AppCompatActivity {
    EditText etMsg;
    Button btnPost;
    User current_user;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    final FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etMsg=findViewById(R.id.etN);
        btnPost=findViewById(R.id.btnPost2);
        //find current user thats logged into the app
        current_user=Singleton.getExisting_user();
//        current_user= new User("USCD","TechFellow"); //only for testing

        //connect to database
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference("schools").child(current_user.getUni()).child("Notifications");

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=etMsg.getText().toString();
                String keyid=myRef.push().getKey().toString();
                myRef.child(keyid).setValue(message);
                Toast.makeText(compose.this,"Notification Posted",Toast.LENGTH_SHORT);
                etMsg.setText("");
                goHome();


            }
        });

    }

    private void goHome() {
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
    }
}