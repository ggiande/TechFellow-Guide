package com.example.treehouseguidebook.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.treehouseguidebook.LoginActivity;
import com.example.treehouseguidebook.R;
import com.example.treehouseguidebook.Singleton;
import com.example.treehouseguidebook.User;
import com.example.treehouseguidebook.compose;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {
    Button btnChange;
    Button btnLogOut;
    Button btnWD;
    Button btnCancel;
    Button btnSet;
    EditText newPwd;
    User current_user;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private SharedPreferences sp;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnChange=view.findViewById(R.id.btnChangePwd);
        btnLogOut=view.findViewById(R.id.btnLogout);
        btnWD=view.findViewById(R.id.btnWithdraw);
        btnCancel=view.findViewById(R.id.btnCancel);
        btnSet=view.findViewById(R.id.btnChng);
        newPwd=view.findViewById(R.id.etNewPwd);


        //Make password helper buttons invisible till its needed
        newPwd.setVisibility(view.INVISIBLE);
        btnCancel.setVisibility(view.INVISIBLE);
        btnSet.setVisibility(view.INVISIBLE);
        sp = getContext().getSharedPreferences("login", MODE_PRIVATE);

        //Current User
        current_user=Singleton.getExisting_user();

        //create ref to the database
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference("schools").child(current_user.getUni());

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.setExisting_user(null);// Logout by setting user as null
                sp.edit().clear().apply();
                sp.edit().putBoolean("logged", false).apply();
                Intent i= new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogOut.setVisibility(v.INVISIBLE);
                btnWD.setVisibility(v.INVISIBLE);
                btnChange.setVisibility(v.INVISIBLE);

                newPwd.setVisibility(v.VISIBLE);
                btnCancel.setVisibility(v.VISIBLE);
                btnSet.setVisibility(v.VISIBLE);


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPwd.setText("");
                newPwd.setVisibility(v.INVISIBLE);
                btnCancel.setVisibility(v.INVISIBLE);
                btnSet.setVisibility(v.INVISIBLE);

                btnLogOut.setVisibility(v.VISIBLE);
                btnWD.setVisibility(v.VISIBLE);
                btnChange.setVisibility(v.VISIBLE);

            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password= newPwd.getText().toString();
                myRef.child(current_user.getUsername()).child("pwd").setValue(password);
                Toast.makeText(getContext(),"Password Updated",Toast.LENGTH_SHORT).show();

                newPwd.setText("");
                newPwd.setVisibility(v.INVISIBLE);
                btnCancel.setVisibility(v.INVISIBLE);
                btnSet.setVisibility(v.INVISIBLE);

                btnLogOut.setVisibility(v.VISIBLE);
                btnWD.setVisibility(v.VISIBLE);
                btnChange.setVisibility(v.VISIBLE);


            }
        });

        btnWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(current_user.getUsername()).removeValue();
                sp.edit().clear().apply();
                sp.edit().putBoolean("logged", false).apply();
                Toast.makeText(getContext(), "Account Deactivated", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });



    }
}