package com.erunetimeterror.wai.fragments;

import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erunetimeterror.wai.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Profile_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv =  view
                .findViewById(R.id.prof_recycler);
        TileContent profile= new TileContent("Michał","ENTJ","pic");
        TileContent dailytip= new TileContent("Daily Tip","Here is daily tip for ur fellows entjs","");
        TileContent graph= new TileContent("Your deailed MBTI results","data","");
        TileContent[] tiles = {profile,dailytip,graph};
        TileAdapter adapter = new TileAdapter(getActivity(),new ArrayList<TileContent>(Arrays.asList(tiles)));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }
}


