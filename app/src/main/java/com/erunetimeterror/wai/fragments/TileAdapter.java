package com.erunetimeterror.wai.fragments;

import android.service.quicksettings.Tile;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erunetimeterror.wai.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TileAdapter extends RecyclerView.Adapter<TileAdapter.ViewHolder> {

    private ArrayList<TileContent> tilecontent = new ArrayList<>();
    private int len;


    public TileAdapter(Context context,ArrayList<TileContent> atilecontent,int alen){
        tilecontent=atilecontent;
        len = alen;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TileContent tilecontent;


        public ViewHolder(View view) {
            super(view);
            //ImageView vpic = view.findViewById(R.id.);

        }
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @NonNull
    @Override
    public TileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int selected_layout = R.layout.profile_tile;
        Log.d("HOLD","HOLD HOLD HOLD");
        switch (viewType){
            case 0:
                //profile view type
                break;

            case 1:
                //profile view type
                break;

            case 2:
                //profile view type
                break;
        }
        Log.d("tag",String.valueOf(viewType));
        View itemView = LayoutInflater.from(parent.getContext()).inflate(selected_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TileAdapter.ViewHolder holder, int position) {
        switch (position){
            case 0:
                //set title to user name
                //set article to mbti type
                //set to userpic
                break;
            case 1:
                //set to
                break;

            case 2:
                //some charts stuff view type
                break;
        }
    }

    @Override
    public int getItemCount() {
        return len;

    }


}