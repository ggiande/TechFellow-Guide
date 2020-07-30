package com.example.treehouseguidebook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.treehouseguidebook.Adapter.MessageAdapter;
import com.example.treehouseguidebook.Adapter.SectionAdapter;
import com.example.treehouseguidebook.Chat;
import com.example.treehouseguidebook.R;
import com.example.treehouseguidebook.Section;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecificSectionFragment extends Fragment {

    String header;
    String section;
    TextView chosen_header;
    DatabaseReference ref;

    SectionAdapter sectionAdapter;
    List<Section> mSection;

    RecyclerView rvSpecific;

    public SpecificSectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specific_section, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar1);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new GuideFragment();
                ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContainer, fragment)
                        .commit();
            }
        });

        rvSpecific = view.findViewById(R.id.rvSpecific);
        rvSpecific.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        rvSpecific.setLayoutManager(linearLayoutManager);

        Bundle bundle = this.getArguments();

        if (bundle != null){
            header = bundle.getString("header");
            section = bundle.getString("section");
            Log.i("Specific", header);
        }
        chosen_header = view.findViewById(R.id.chosen_header);
        chosen_header.setText(header);
        // Inflate the layout for this fragment
        ref = FirebaseDatabase.getInstance().getReference("Guides")
                .child(section);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mSection = new ArrayList<>();
                for(DataSnapshot s: snapshot.getChildren()){
                    if (!s.getKey().equals("Header")){
//                        Log.i(s.child(""))
                        if(s.child("link").exists()){
                            Section sec = s.getValue(Section.class);
                            mSection.add(sec);
//                            Log.i("TAG", s.getValue().toString());
                        }
//                        for(DataSnapshot s1: s.getChildren()){
//                            Log.i("TAG", s1.getKey());
//                        }
                    }
                }
                sectionAdapter = new SectionAdapter(getContext(), mSection);
                rvSpecific.setAdapter(sectionAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}