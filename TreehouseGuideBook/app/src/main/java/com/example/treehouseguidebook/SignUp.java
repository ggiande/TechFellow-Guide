package com.example.treehouseguidebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText etName;
    EditText etEmail;
    EditText etUserId;
    EditText etPassword;
    EditText etSchool;
    EditText etRole;
    Button btnReg;
    private DatabaseReference myRef;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etUserId=findViewById(R.id.etUN);
        etPassword=findViewById(R.id.etNewPwd);
        etSchool=findViewById(R.id.etSchool);
        etRole=findViewById(R.id.etRole);
        btnReg=findViewById(R.id.btnReg);
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("schools");
        // User(String username,String name, String uni, String pwd, String role) {

            btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Button works",Toast.LENGTH_LONG).show();
                String school=etSchool.getText().toString();
//                String role=etRole.getText().toString();
//                User curr= new User(etUserId.getText().toString(),
//                        etName.getText().toString(),
//                        etEmail.getText().toString(),
//                        school,
//                        etPassword.getText().toString(),
//                        role);
                String keyid=myRef.push().getKey().toString();
                Toast.makeText(getApplicationContext(),keyid,Toast.LENGTH_LONG).show();




            }
        });

    }
}