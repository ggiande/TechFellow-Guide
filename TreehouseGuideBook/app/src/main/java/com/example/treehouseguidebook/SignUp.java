package com.example.treehouseguidebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {
    EditText etName;
    EditText etEmail;
    EditText etUserId;
    EditText etPassword;
    EditText etSchool;
    TextView etRole;
    Spinner opRole;
    Button btnReg;
    String role;
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
        opRole=findViewById(R.id.opRole);
        btnReg=findViewById(R.id.btnReg);
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("schools");
                //.child(school).child(role);


        //create adapter for roles spinner
        String[] roles= new String[]{"Chose a Role","Student","TechFellow"};
        ArrayAdapter<String> role_adapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,roles);
        opRole.setAdapter(role_adapter);
        opRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role=(String) parent.getItemAtPosition(position);
                //etRole.setText(role);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ;




            btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Button works",Toast.LENGTH_LONG).show();
                String school=etSchool.getText().toString();
                final User curr= new User(etUserId.getText().toString(),
                        etName.getText().toString(),
                        etEmail.getText().toString(),
                        school,
                        etPassword.getText().toString(),
                        role);
                //read data from schools node to find current schools
                myRef.child(curr.getUni()).child(role).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d: snapshot.getChildren())
                        {
                            Log.d("Login",d.getKey());
                            if( curr.getUsername().equals(d.getKey().toString()))
                            {
                                Toast.makeText(getApplicationContext(),"Username Already exists",Toast.LENGTH_SHORT).show();
                                etUserId.setText("");
                                etPassword.setText("");
                                return;

                            }

                            if(d.getValue(User.class).getEmail().equals(curr.getEmail())){
                                Toast.makeText(getApplicationContext(),"Account already exists",Toast.LENGTH_SHORT).show();
                                etUserId.setText("");
                                etPassword.setText("");
                                etEmail.setText("");
                                return;

                            }

                            //User u=d.getValue(User.class);


                        }
                        myRef.child(curr.getUni()).child(role).child(curr.getUsername()).setValue(curr);
                        Singleton.setExisting_user(curr);
                        goHome();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






            }
        });

    }

    private void goHome() {
            Intent i= new Intent(this,MainActivity.class);
            startActivity(i);
            finish();

    }
}