package com.erunetimeterror.wai.fragments;

import android.media.Image;
import android.service.quicksettings.Tile;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erunetimeterror.wai.R;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class TileAdapter extends RecyclerView.Adapter<TileAdapter.ViewHolder> {

    private ArrayList<TileContent> tilecontent = new ArrayList<>();
    private int len;


    public TileAdapter(Context context, ArrayList<TileContent> atilecontent, int len){
        tilecontent=atilecontent;
        this.len = len;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TileContent tilecontent;
        public TextView title;
        public TextView content;
        public ImageView pic;
        public TextView expand;
        public ImageButton expander;
        public RecyclerView charts;
        boolean state = true;


        public ViewHolder(View view) {
            super(view);
            int cv = (int) view.getTag();
            switch (cv){
                case 0: //profile
                    pic = view.findViewById(R.id.profilepic);
                    break;
                case 1: //daily
                     expand = view.findViewById(R.id.detailed);
                     expander = view.findViewById(R.id.downarrow);
                     state = false;
                    break;
                case 2: //graph
                    charts = view.findViewById(R.id.chart_recycle);
                    break;

            }
            title = view.findViewById(R.id.ttitle);
            content = view.findViewById(R.id.tarticle);

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
        int tager = 0;
        switch (viewType){
            case 0:
                selected_layout = R.layout.profile_tile;

                break;

            case 1:
                 selected_layout = R.layout.daily_tile;
                break;

            case 2:
                selected_layout = R.layout.chart_tile;
                break;
        }
        Log.d("tag",String.valueOf(viewType));
        View itemView = LayoutInflater.from(parent.getContext()).inflate(selected_layout, parent, false);
        itemView.setTag(viewType);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TileAdapter.ViewHolder holder, int position) {
        switch (position){
            case 0:
                holder.title.setText("Michał Posłuszny");
                holder.content.setText("ENTJ");
                //set title to user name
                //set article to mbti type
                //set to userpic
                break;
            case 1:
                //set to
                holder.title.setText("Daily Tips");
                holder.content.setText("Commanders are natural-born leaders. People with this personality type embody the gifts of charisma and confidence, and project authority in a way that draws crowds together behind a common goal. ");
                holder.expand.setText("However, Commanders are also characterized by an often ruthless level of rationality, using their drive, determination and sharp minds to achieve whatever end they’ve set for themselves. Perhaps it is best that they make up only three percent of the population, lest they overwhelm the more timid and sensitive personality types that make up much of the rest of the world – but we have Commanders to thank for many of the businesses and institutions we take for granted every day.");
                View.OnClickListener DoExpand = new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ImageButton b = (ImageButton )v;
                        View parent = (View)v.getParent();
                        if (holder.state){
                            holder.expand.setVisibility(View.GONE);
                            holder.state = false;
                        }else{
                            holder.expand.setVisibility(View.VISIBLE);
                            holder.state = true;

                        }
                        }
                };

                holder.expander.setOnClickListener(DoExpand);
                break;

            case 2:
                //some charts stuff view type
                holder.title.setText("Your MBTI stats");
                holder.content.setText("");
                Integer[] left = { 4,30,4,14 }; //INFP
                Integer[] right = { 15,5,16,18 }; //ESTJ
                ArrayList<Integer>  aleft = new ArrayList<Integer>(Arrays.asList(left));
                ArrayList<Integer>  aright = new ArrayList<Integer>(Arrays.asList(right));
                ChartAdapter adapter = new ChartAdapter(holder.itemView.getContext(),aleft,aright);
                LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL,false){

                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                holder.charts.setLayoutManager(layoutManager);
                holder.charts.setAdapter(adapter);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return len;

    }


}