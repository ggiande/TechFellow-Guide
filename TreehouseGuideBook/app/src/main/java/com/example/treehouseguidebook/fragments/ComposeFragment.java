package com.example.treehouseguidebook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.treehouseguidebook.R;
import com.example.treehouseguidebook.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComposeFragment extends Fragment {

    EditText etMsg;
    Button btnPost;
    User current_user;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    public ComposeFragment() {
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
        return inflater.inflate(R.layout.fragment_compose, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etMsg=view.findViewById(R.id.etNotify);
        btnPost=view.findViewById(R.id.button);
        //find current user thats logged into the app
        //current_user=Singleton.getExisting_user();
        current_user= new User("USCD","TechFellow"); //only for testing

        //connect to database
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference("schools").child(current_user.getUni()).child("Notifications");

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=etMsg.getText().toString();
                String keyid=myRef.push().getKey().toString();
                myRef.child(keyid).setValue(message);
                Fragment fragment = new NotificationsFragment();



            }
        });
    }
}