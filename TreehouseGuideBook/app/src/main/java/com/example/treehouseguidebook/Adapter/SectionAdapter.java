package com.example.treehouseguidebook.Adapter;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treehouseguidebook.R;
import com.example.treehouseguidebook.Section;
import com.example.treehouseguidebook.User;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {

    private Context mContext;
    private List<Section> mSection;
    public User current_user;

    public SectionAdapter(Context context, List<Section> mSection){
        this.mContext = context;
        this.mSection = mSection;
    }

    @NonNull
    @Override
    public SectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.specific_section, parent, false);
        return new SectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionAdapter.ViewHolder holder, int position) {
        Section section = mSection.get(position);

        holder.description.setText(section.getDesc());
        holder.link.setText(section.getLink());

    }

    @Override
    public int getItemCount() {
        return mSection.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView description;
        public TextView link;
        public ImageButton btnBookmark;


        public ViewHolder(View itemView){
            super(itemView);

            description = itemView.findViewById(R.id.description);
            link = itemView.findViewById(R.id.link);
            btnBookmark=itemView.findViewById(R.id.btnBookmark);
            link.setMovementMethod(LinkMovementMethod.getInstance());

            btnBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
