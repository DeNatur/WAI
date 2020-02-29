package com.erunetimeterror.wai.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.erunetimeterror.wai.R;
import com.erunetimeterror.wai.fragments.ChatFragment;
import com.erunetimeterror.wai.fragments.MapsFragments;
import com.erunetimeterror.wai.fragments.Profile_Fragment;
import com.erunetimeterror.wai.fragments.WikiFragment;

public class MainActivity extends AppCompatActivity {
    FragmentsPagerAdapter fragmentsPagerAdapter;
    ViewPager viewPager;
    ImageView profile, wiki, map, chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        fragmentsPagerAdapter = new FragmentsPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.pager);
        profile = findViewById(R.id.profile);
        wiki = findViewById(R.id.wiki);
        map = findViewById(R.id.map);
        chat = findViewById(R.id.chat);
        viewPager.setAdapter(fragmentsPagerAdapter);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        wiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });

    }
    class FragmentsPagerAdapter extends FragmentStatePagerAdapter {
        Fragment[] fragments = new Fragment[4];
        public FragmentsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment;
            switch (i){
                case 0:
                    fragment = new Profile_Fragment();
                    break;
                case 1:
                    fragment = new WikiFragment();
                    break;
                case 2:
                    fragment = new MapsFragments();
                    break;
                case 3:
                    fragment = new ChatFragment();
                    break;
                default:
                    fragment = new MapsFragments();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
