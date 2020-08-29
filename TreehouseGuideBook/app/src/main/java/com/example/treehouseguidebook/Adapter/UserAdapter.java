package com.example.treehouseguidebook.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.util.Log;
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
    User user;
    User currentUser;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
         user = mUsers.get(position);
//         Log.i("chatch", user.getName());
         currentUser = Singleton.getExisting_user();
        if (user.getEmail().equals(currentUser.getEmail())){
            holder.username.setText(user.getRole() + user.getName());
        }
        else{
            holder.username.setText(user.getName() + " - " + user.getRole());
        }

//        Log.i("TAGGG", String.valueOf(holder.getAdapterPosition()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = mUsers.get(holder.getAdapterPosition());
//                Log.i("TAGGG", String.valueOf(holder.getAdapterPosition()));
                Fragment fragment = new ChatFragment();
                Bundle bundle = new Bundle();
                Log.i("CHECKVIEW", view.toString());
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
