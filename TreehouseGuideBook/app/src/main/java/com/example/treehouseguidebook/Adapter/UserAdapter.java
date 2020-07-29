package com.example.treehouseguidebook.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treehouseguidebook.R;
import com.example.treehouseguidebook.Singleton;
import com.example.treehouseguidebook.User;
import com.example.treehouseguidebook.fragments.ChatFragment;

import java.util.List;
import java.util.Objects;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;

    public UserAdapter(Context mContext, List<User> mUsers){
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = mUsers.get(position);
        User currentUser = Singleton.getExisting_user();
        if (user.getEmail().equals(currentUser.getEmail())){
            holder.username.setText(user.getRole() + user.getName());
        }
        else{
            holder.username.setText(user.getName() + " - " + user.getRole());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ChatFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", user.getUsername());
                fragment.setArguments(bundle);
                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContainer, fragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;

        public ViewHolder(View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.username);
        }

    }

}
