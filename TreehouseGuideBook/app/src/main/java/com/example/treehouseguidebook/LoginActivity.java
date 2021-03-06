package com.example.treehouseguidebook;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText etUser;
    EditText etPwd;
    EditText etUni;
    Button techLogin;
    Button StudentLogin;
    Button btnSignup;
    String userName;
    String userPassword;
    String school;
    SharedPreferences sp;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private String userID;
    private String userSCHOOL;
    private Boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUser=findViewById(R.id.etUser);
        etPwd=findViewById(R.id.etPwd);
        etUni=findViewById(R.id.etUni);
        techLogin=findViewById(R.id.btnTLogin);
        StudentLogin=findViewById(R.id.btnSlogin);
        btnSignup=findViewById(R.id.btnSign);

        database=FirebaseDatabase.getInstance();

        sp = getSharedPreferences("login", MODE_PRIVATE);

        userID = sp.getString("userId", null);
        userSCHOOL = sp.getString("UserSchool", null);
        loggedIn = sp.getBoolean("logged", false);

        Log.i("G", sp.getString("userId", " "));
        if(userID != null && userSCHOOL != null && loggedIn){
            goHome();
            return;
        }

//        User cur = Singleton.getExisting_user();
//
//        Log.i("Testing", cur.getName() + "name");

        userName=etUser.getText().toString();
        userPassword=etPwd.getText().toString();
        school=etUni.getText().toString();
        Log.d("Login"," username= "+userName);

        techLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=etUser.getText().toString();
                userPassword=etPwd.getText().toString();
                school=etUni.getText().toString();
                Log.d("Login"," username= "+userName);
                myRef=database.getReference("schools").child(school).child("TechFellow");
                Auth(myRef,userName,userPassword);
                etPwd.setText("");
                etUser.setText("");
                Toast.makeText(getApplicationContext(),"Incorrect Login",Toast.LENGTH_SHORT).show();
            }
        });

        StudentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=etUser.getText().toString();
                userPassword=etPwd.getText().toString();
                school=etUni.getText().toString();
                Log.d("Login"," username= "+userName);
                //myRef=database.getReference("schools").child(etUni.getText().toString()).child("Student");
                myRef=database.getReference("schools").child(school).child("Student");
                Auth(myRef,userName,userPassword);

                //Auth(myRef,etUser.getText().toString(),etPwd.getText().toString());


            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUp();
            }
        });


    }


    public void Auth(DatabaseReference database, final String userId, final String password)
    {
          //read data from schools node to find current schools
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d: snapshot.getChildren())
                {
                    Log.d("Login",d.getKey());
                    if( userId.equals(d.getKey().toString()))
                    {
                        Toast.makeText(getApplicationContext(),"correct username",Toast.LENGTH_SHORT).show();
                        User u=d.getValue(User.class);
                        Log.d("LoginPwd",u.getPwd());
                        if(u.getPwd().equals(password)){
                            Singleton.setExisting_user(u);
                            sp.edit().putString("userId", userId).apply();
                            sp.edit().putString("UserSchool", u.getUni()).apply();
                            sp.edit().putBoolean("logged", true).apply();
                            goHome();
                            return;

                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    public void goHome()
    {
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void goToSignUp()
    {
        Intent i=new Intent(this,SignUp.class);
        i.putExtra("username",etUser.getText().toString());
        i.putExtra("uni",etUni.getText().toString());
        startActivity(i);
    }
}

