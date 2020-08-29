package com.example.treehouseguidebook.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.treehouseguidebook.Adapter.MessageAdapter;
import com.example.treehouseguidebook.Chat;
import com.example.treehouseguidebook.LoginActivity;
import com.example.treehouseguidebook.R;
import com.example.treehouseguidebook.Singleton;
import com.example.treehouseguidebook.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatFragment extends Fragment {

    TextView username;

    User fuser;
    DatabaseReference ref;

    ImageButton btn_send;
    EditText text_send;

    String Rusername;
    MessageAdapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new UsersFragment();
                ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContainer, fragment)
                        .commit();
            }
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        fuser = Singleton.getExisting_user();

        username = view.findViewById(R.id.username);
        btn_send = view.findViewById(R.id.btn_send);
        text_send = view.findViewById(R.id.text_send);
//        fuser = Singleton.getExisting_user();
        Bundle bundle = this.getArguments();

        if (bundle != null){
            Rusername = bundle.getString("username");
            Log.i("checkingchat", Rusername);
        }

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                if(!msg.equals("")){
                    // change "rodrigo" to fuser.getEmail()
                    sendMessage(fuser.getUsername(), Rusername, msg);
                } else {
                    Toast.makeText(getContext(), "Empty text", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });


        ref = FirebaseDatabase.getInstance().getReference("schools")
                // change school to user.getuni()
                .child(fuser.getUni());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if(snapshot.getKey().equals("TechFellow")){
                        for(DataSnapshot s: snapshot.getChildren()) {
                            User user = s.getValue(User.class);
                            Log.i("ChatFragment", "2nd " + Rusername);
                            Log.i("Chat", "3rd " + user.getUsername());
                            if(user.getUsername() != null){
                                if (user.getUsername().equals(Rusername)) {
                                    username.setText(user.getName());
                                    readMessages(fuser.getUsername(), Rusername);
                                }
                            }
                            // change school to fuser.getEmail()
                        }
                    }
                }
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if(snapshot.getKey().equals("Student")){
                        for(DataSnapshot s: snapshot.getChildren()){
                            User user = s.getValue(User.class);
                            if(user.getUsername() != null){
                                if (user.getUsername().equals(Rusername)) {
                                    Log.i("Checking", user.getName());
                                    username.setText(user.getName());
                                    readMessages(fuser.getUsername(), Rusername);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        ref = FirebaseDatabase.getInstance().getReference("schools")
//                // change school to user.getuni()
//                .child("CSUMB")
//                .child("TechFellow");
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    User user = dataSnapshot.getValue(User.class);
//                    if(user.getEmail() == userEmail){
//                        username.setText(user.getName());
//                    }
//                    // change school to fuser.getEmail()
//                    readMessages("Rodrigo", userEmail);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        return view;
    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

//        ref.child("Chats").child(fuser.getuni()).child(fuser.getemail()).push.setValue(hashMap);
        ref.child("Chats").child(fuser.getUni()).child(fuser.getUsername()).push().setValue(hashMap);
        ref.child("Chats").child(fuser.getUni()).child(Rusername).push().setValue(hashMap);
    }

    private void readMessages(final String myid, final String userId){
        mchat = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Chats")
                .child(fuser.getUni())
                .child(fuser.getUsername());
//                .child(fuser.getUni())
//                .child(fuser.getEmail());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mchat.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
//                    Log.i("CHATS", chat.getMessage());
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userId) ||
                            chat.getReceiver().equals(userId) && chat.getSender().equals(myid)){
                        mchat.add(chat);
                    }

                    messageAdapter = new MessageAdapter(getContext(), mchat);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}