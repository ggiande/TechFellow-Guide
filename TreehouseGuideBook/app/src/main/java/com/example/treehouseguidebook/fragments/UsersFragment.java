package com.example.treehouseguidebook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.treehouseguidebook.Adapter.UserAdapter;
import com.example.treehouseguidebook.R;
import com.example.treehouseguidebook.Singleton;
import com.example.treehouseguidebook.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<User> mUsers;
    private UserAdapter userAdapter;
    private User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        currentUser = Singleton.getExisting_user();
//        Log.i("UsersFragment", currentUser.getName());

        mUsers = new ArrayList<>();

        readUsers();

        return view;
    }

    private void readUsers() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("schools")
                .child(currentUser.getUni());
//                .child("TechFellow");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if(snapshot.getKey().equals("TechFellow")){
                        for(DataSnapshot s: snapshot.getChildren()){
//                            Log.i("Tag", String.valueOf(s));
                            User user = s.getValue(User.class);
                            Log.i("Tag", user.getName() + "TF");
                            if(!user.getEmail().equals(currentUser.getEmail())){
                                mUsers.add(user);
                            }

                        }
                    }
                }
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if(snapshot.getKey().equals("Student")){
                        for(DataSnapshot s: snapshot.getChildren()){
//                            Log.i("Tag", String.valueOf(s));
                            User user = s.getValue(User.class);
                            Log.i("Tag", user.getName() + "S");
                            if(!user.getEmail().equals(currentUser.getEmail())){
                                mUsers.add(user);
                            }

                        }
                    }
                }
//                String value = snapshot.getValue();
                userAdapter = new UserAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("USERSFRAG", error.toString());
            }
        });
    }
}