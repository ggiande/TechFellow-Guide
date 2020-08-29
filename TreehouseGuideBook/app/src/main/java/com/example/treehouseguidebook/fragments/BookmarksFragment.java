package com.example.treehouseguidebook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.treehouseguidebook.Adapter.BookmarkAdapter;
import com.example.treehouseguidebook.Adapter.HeaderAdapter;
import com.example.treehouseguidebook.R;
import com.example.treehouseguidebook.Section;
import com.example.treehouseguidebook.Singleton;
import com.example.treehouseguidebook.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BookmarksFragment extends Fragment {
    RecyclerView rvMarks;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public User current_user;
    public BookmarkAdapter adapter;
    public List<Section> Sections;


    public BookmarksFragment() {
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
        return inflater.inflate(R.layout.fragment_bookmarks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMarks=view.findViewById(R.id.rvMarks);
        current_user= Singleton.getExisting_user();

        //set layout manger and divider
        rvMarks.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMarks.addItemDecoration(new DividerItemDecoration(rvMarks.getContext(), DividerItemDecoration.VERTICAL));


        database = FirebaseDatabase.getInstance();
        myRef=database.getReference("schools").child(current_user.getUni()).child(current_user.getRole()).child(current_user.getUsername());


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Sections=new ArrayList<Section>();

        myRef.child("bookmarks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Sections.clear();
               for(DataSnapshot d: snapshot.getChildren()){
                   String desc=d.getKey().toString();
                   Log.d("bookmark frag",desc);
                   String link=  d.getValue().toString();
                   Log.d("bookmark frag",link);

                   //Section sec = new Section(desc,link);
                   Sections.add(new Section(desc,link));
               }
                adapter = new BookmarkAdapter(getContext(), Sections);
                rvMarks.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}