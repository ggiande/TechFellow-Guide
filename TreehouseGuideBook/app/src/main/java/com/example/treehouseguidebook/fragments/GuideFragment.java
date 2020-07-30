package com.example.treehouseguidebook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.treehouseguidebook.Adapter.HeaderAdapter;
import com.example.treehouseguidebook.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class GuideFragment extends Fragment {
    public static final String TAG ="GuideFragment";
    private DatabaseReference ref;
    private FirebaseDatabase database;
    HeaderAdapter headerAdapter;
    List<String> headers;

    RecyclerView rvSections;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);

        rvSections = view.findViewById(R.id.rvSections);
        rvSections.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        rvSections.setLayoutManager(linearLayoutManager);
        database = FirebaseDatabase.getInstance();

        ref=database.getReference("Guides");
        headers = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    if (s.child("Header").getKey().equals("Header")){
//                        Log.i(TAG, s.child("Header").getValue().toString());
                        headers.add(s.child("Header").getValue().toString());
                    }
                }
                headerAdapter = new HeaderAdapter(getContext(), headers);
                rvSections.setAdapter(headerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}