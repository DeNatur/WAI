package com.erunetimeterror.wai.fragments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erunetimeterror.wai.R;
import com.erunetimeterror.wai.activities.ChatActivity;
import com.erunetimeterror.wai.activities.GameActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    private int len;
    private ArrayList<String> names;
    private ArrayList<String> msg;
    private Context ctx;

    public FriendsAdapter(Context context, ArrayList<String> anames,ArrayList<String> amsg,int alen){
        names = anames;
        msg = amsg;
        len = alen;
        ctx = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView message;
        public ImageButton chat;
        public ImageButton play;
        public CircleImageView pic;


        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            message = view.findViewById(R.id.message);
            pic = view.findViewById(R.id.selfie);
            chat = view.findViewById(R.id.chat);
            play = view.findViewById(R.id.play);
        }
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_tile, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FriendsAdapter.ViewHolder holder, int position) {
        holder.name.setText(names.get(position));
        holder.message.setText(msg.get(position));
        View.OnClickListener DoChat = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ChatActivity.class);
                ctx.startActivity(intent);
            }
        };
        holder.chat.setOnClickListener(DoChat);

        View.OnClickListener DoGame = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, GameActivity.class);
                ctx.startActivity(intent);
            }
        };
        holder.play.setOnClickListener(DoGame);
        switch(holder.getAdapterPosition()){
            case 0:
                holder.pic.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.mymon));
                break;
            case 1:
                holder.pic.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.nati));
                break;
            case 2:
                holder.pic.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.kondziu));
                break;
            default:
                holder.pic.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.noone));
                break;

        }
    }

    @Override
    public int getItemCount() {
        return len;

    }


}