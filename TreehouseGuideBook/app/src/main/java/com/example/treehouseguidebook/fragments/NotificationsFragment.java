package com.example.treehouseguidebook.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.treehouseguidebook.NotificationsAdapter;
import com.example.treehouseguidebook.R;
import com.example.treehouseguidebook.Singleton;
import com.example.treehouseguidebook.User;
import com.example.treehouseguidebook.compose;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {

  RecyclerView rvNotifications;
  User current_user;
  ImageButton btnCompose;
  protected List<String> allPosts;
  protected NotificationsAdapter adapter;
  private DatabaseReference myRef;
  private FirebaseDatabase database;





    public NotificationsFragment() {
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
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Log.d("NotFrag","enter");

        //find current user thats logged into the app
        //current_user=Singleton.getExisting_user();
        current_user= new User("USCD","TechFellow"); //only for testing



        rvNotifications =view.findViewById(R.id.rvNotfications);
        allPosts= new ArrayList<>();
        Log.d("NotFrag","declare btn");
        btnCompose= view.findViewById(R.id.btnCompose);

        //set visibitly of Compose button
        //Students cannot compose messages
        Log.d("NotFrag","before button");

        if(current_user.getRole().equals("Student"))
        {
            Log.d("NotFrag","enter invisible");

            btnCompose.setVisibility(View.INVISIBLE);
        }

        //Compose a message
        btnCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NotFrag","enter compose activity button");
                Compose();
            }
        });


        //set layout manger and divider
        rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNotifications.addItemDecoration(new DividerItemDecoration(rvNotifications.getContext(), DividerItemDecoration.VERTICAL));



        //connect to database
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference("schools").child(current_user.getUni()).child("Notifications");


        //read database to find notifications
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allPosts.clear();
                for (DataSnapshot d: snapshot.getChildren())
                {
                    String msg=d.getValue().toString();
                    allPosts.add(msg);
                }

                //create and connect recycler view to adapter
                adapter= new NotificationsAdapter(getContext(),allPosts);
                rvNotifications.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }

    private void Compose() {
        Log.d("NotFrag","enter compose activity");
        Intent i= new Intent(getContext(), compose.class);
        startActivity(i);

    }


}