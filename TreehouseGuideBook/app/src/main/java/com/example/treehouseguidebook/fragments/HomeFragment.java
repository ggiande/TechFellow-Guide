package com.example.treehouseguidebook.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.treehouseguidebook.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    GridView gvItems;
    List<String> allGuides= new ArrayList<String>()
    {
        {
            add("Geeks");
            add("for");
            add("Geeks");
        }
    };



    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}