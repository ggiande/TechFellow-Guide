package com.example.treehouseguidebook.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treehouseguidebook.R;
import com.example.treehouseguidebook.Section;
import com.example.treehouseguidebook.Singleton;
import com.example.treehouseguidebook.User;
import com.example.treehouseguidebook.fragments.WebFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private Context mContext;
    private List<Section> mSection;
    public User current_user;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    public BookmarkAdapter(Context mContext, List<Section> mSection) {
        this.mContext = mContext;
        this.mSection = mSection;
        this.current_user= Singleton.getExisting_user();
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference("schools").child(current_user.getUni()).child(current_user.getRole()).child(current_user.getUsername()).child("bookmarks");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.specific_section, parent, false);
        return new BookmarkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Section section = mSection.get(position);

        holder.description.setText(section.getDesc());
        holder.link.setText(section.getLink());


    }

    @Override
    public int getItemCount() {
        return mSection.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView description;
        public TextView link;
        public ImageButton btnBookmark;
        public RelativeLayout section_unit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.description);
            link = itemView.findViewById(R.id.link);
            btnBookmark=itemView.findViewById(R.id.btnBookmark); btnBookmark.setColorFilter(Color.RED);
            section_unit=itemView.findViewById(R.id.sectiom_unit);

            section_unit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"LINK CLICKED ",Toast.LENGTH_SHORT).show();
                    //Fragment fragment= new WebFragment();
                    WebFragment fragment= WebFragment.newInstance(link.getText().toString());

                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flContainer,fragment).commit();
                }
            });


            btnBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key=description.getText().toString();
                    myRef.child(key).removeValue();
                    mSection.remove(getAdapterPosition());

                }
            });
        }
    }
}
