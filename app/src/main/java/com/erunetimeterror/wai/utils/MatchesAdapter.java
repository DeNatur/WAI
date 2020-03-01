package com.erunetimeterror.wai.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erunetimeterror.wai.R;
import com.google.android.libraries.places.api.model.Place;

import java.util.ArrayList;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    ArrayList<String> names;
    ArrayList<String> types;

    public MatchesAdapter(ArrayList<String> names,  ArrayList<String> types) {
        this.names = names;
        this.types = types;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = names.get(holder.getAdapterPosition());
        String type = types.get(holder.getAdapterPosition());
        holder.type.setText(type);
        holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, type;
        ImageView add;
        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            type = view.findViewById(R.id.type);
            add = view.findViewById(R.id.add);
        }
    }
}
