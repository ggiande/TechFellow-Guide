package com.example.treehouseguidebook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.treehouseguidebook.NotificationsAdapter;
import com.example.treehouseguidebook.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuideFragment extends Fragment {
    public static final String TAG ="GuideFragment";
    Button btnTest;
    //elements to access firebase
    private DatabaseReference myRef;
    private FirebaseDatabase database;
//    TODO: Make a recycler view within this fragment that will show the guide





    public GuideFragment() {
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
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnTest=view.findViewById(R.id.btnTest);

        //connect to database
        database = FirebaseDatabase.getInstance();
        //create a reference to "Guide" Node in the database.
        //Can access only the children of "Guides" with this Reference
        //to create a reference to one of guides child -> myRef=database.getReference("Guides").child(keyname)
        myRef=database.getReference("Guides");

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Read keys and values from the reference
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //loop through the children
                        //snapshot is the current state of the database
                        //clear the list each time to avoid repeats.
                        for (DataSnapshot d: snapshot.getChildren())
                        {
                            Log.d("Firebase Test section",d.getKey()); //get the key of the current child node.

                            String msg=d.getValue().toString(); //get the values associated with current child node

                            //Log.d("Firebase Test Val",msg); //get the key of the current child node.

                            for( DataSnapshot d2:d.getChildren()){
                                Log.d("Firebase Test random",d2.getKey()); //get the random key associated with each link-desc pair.
                                Log.d("Firebase Test main Val",d2.getValue().toString()); //get link/desc pair


                            }
                        }

                        //create and connect recycler view to adapter here


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



    }

}