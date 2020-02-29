package com.erunetimeterror.wai.fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erunetimeterror.wai.R;

import java.util.ArrayList;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ViewHolder> {
    private  ArrayList<Integer> val_left;
    private ArrayList<Integer> val_right;

    public  ChartAdapter(Context context,  ArrayList<Integer> aright,  ArrayList<Integer> aleft){
        val_right = aright;
        val_left = aleft;

    };

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView chart_text;
        public ImageView chart_left;
        public ImageView chart_right;


        public ViewHolder(View v){
            super(v);
            chart_right = v.findViewById(R.id.chart_right);
            chart_left = v.findViewById(R.id.chart_left);
            chart_text = v.findViewById(R.id.chart_text);

        }

    }

    @NonNull
    @Override
    public ChartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int selected_layout = R.layout.single_chart;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(selected_layout, parent, false);
        return new ChartAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartAdapter.ViewHolder holder, int position) {
        float lefty=(float)val_left.get(holder.getAdapterPosition());
        float righty=(float) val_right.get(holder.getAdapterPosition());
        float sum =  lefty+righty;
        float leftav = lefty/sum;
        float rightav = righty/sum;
        switch (position){
               case 0:
                   holder.chart_right.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.rect_orange));
                   holder.chart_left.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.rect_orange));
                   holder.chart_text.setText("Introvertion | Extravertion");
                   break;
               case 1:
                   holder.chart_right.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.rect_blue));
                   holder.chart_left.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.rect_blue));
                   holder.chart_text.setText("Intuition | Sensing");
                   break;
               case 2:
                   holder.chart_right.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.rect_green));
                   holder.chart_left.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.rect_green));
                   holder.chart_text.setText("Feeling | Thinking");
                   break;
               case 3:
                   holder.chart_right.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.rect_purple));
                   holder.chart_left.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.rect_purple));
                   holder.chart_text.setText("Perceiving | Judging");
                   break;
           }
        holder.chart_right.requestLayout();
        holder.chart_left.requestLayout();
        int rwidth = holder.chart_right.getLayoutParams().width;
        int lwidth = holder.chart_left.getLayoutParams().width;
        Log.d("res","RL");
        Log.d("res", toString().valueOf(rightav * rwidth));
        Log.d("res", toString().valueOf(leftav * lwidth));
        holder.chart_right.getLayoutParams().width =(int) ((leftav * lwidth)/1.3); ;
        holder.chart_left.getLayoutParams().width =(int) ((rightav * rwidth)/1.3);
       // holder.chart_right.widthholder.chart_right.getMaxWidth()

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
