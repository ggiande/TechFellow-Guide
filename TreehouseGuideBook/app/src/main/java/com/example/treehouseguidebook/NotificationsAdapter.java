package com.example.treehouseguidebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    Context context;
    List<String> posts;
    TextView tvMessage;
    User current_user;


    public NotificationsAdapter(){ }

    public NotificationsAdapter(Context context, List<String> posts) {
        this.context = context;
        this.posts = posts;



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_unit,parent,false);
        return (new ViewHolder(view));    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String msg=posts.get(position);
        holder.bind(msg);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage=itemView.findViewById(R.id.notification_msg);

        }

        public void bind(String msg) {
            tvMessage.setText(msg);

        }
    }
}

