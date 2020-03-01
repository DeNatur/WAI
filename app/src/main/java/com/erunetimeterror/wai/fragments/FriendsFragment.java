package com.erunetimeterror.wai.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erunetimeterror.wai.R;

import java.util.ArrayList;
import java.util.Arrays;

public class FriendsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.friends_fragment, container, false);
        RecyclerView rv =  v.findViewById(R.id.friends_recycle);
        String[] names = {"Szymon INFP","Natalia INFP","Konrad ISTJ","Kuba ESFJ","Patrycja ENFP","Asia ENTJ"};
        String[] msg = {"Hej, widziałeś już ten nowy film o...","Idziemy na planszówki jutro? :D","Ta książka którą przeczytałem jest genialna, musisz...","","",""};
        ArrayList<String> anames = new ArrayList<String>(Arrays.asList(names));
        ArrayList<String> amsg = new ArrayList<String>(Arrays.asList(msg));
        FriendsAdapter adapter = new FriendsAdapter(getActivity(),anames,amsg,anames.size());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        return v;
    }}

