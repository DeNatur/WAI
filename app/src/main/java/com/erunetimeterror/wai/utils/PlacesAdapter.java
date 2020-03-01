package com.erunetimeterror.wai.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erunetimeterror.wai.R;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;

import org.w3c.dom.Text;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {
    ArrayList<Place> placesList;
    ArrayList<String> attributes;
    ArrayList<Bitmap> bitmaps;

    public PlacesAdapter(ArrayList<Place> placesList, ArrayList<String> attributes, ArrayList<Bitmap> bitmaps) {
        this.placesList = placesList;
        this.attributes = attributes;
        this.bitmaps = bitmaps;
    }
    public void addItem(Place place, String attribute, Bitmap bitmap, int index){
        placesList.add(place);
        attributes.add(attribute);
        bitmaps.add(bitmap);
        notifyItemInserted(index);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Place place = placesList.get(holder.getAdapterPosition());
        holder.placeName.setText(place.getName());
        //holder.placeDescription.setText(attributes.get(holder.getAdapterPosition()));
        holder.placeImage.setImageBitmap(bitmaps.get(holder.getAdapterPosition()));
        holder.webBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(android.content.Intent.ACTION_VIEW, place.getWebsiteUri());
                v.getContext().startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeName;
        TextView placeDescription;
        ImageView placeImage;
        Button webBtn;
        public ViewHolder(View view) {
            super(view);

            placeName = view.findViewById(R.id.placeName);
            placeDescription = view.findViewById(R.id.placeDescription);
            placeImage = view.findViewById(R.id.placeImage);
            webBtn = view.findViewById(R.id.webBt);
        }
    }
}
