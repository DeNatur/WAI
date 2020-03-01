package com.erunetimeterror.wai.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.erunetimeterror.wai.MainApplication;
import com.erunetimeterror.wai.R;
import com.erunetimeterror.wai.fragments.ChatFragment;
import com.erunetimeterror.wai.fragments.FriendsFragment;
import com.erunetimeterror.wai.fragments.MapsFragments;
import com.erunetimeterror.wai.fragments.Profile_Fragment;
import com.erunetimeterror.wai.fragments.WikiFragment;

public class MainActivity extends AppCompatActivity {
    FragmentsPagerAdapter fragmentsPagerAdapter;
    ViewPager viewPager;
    private int locationRequestCode = 1000;
    ImageView profile, wiki, map, chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        MainApplication app = (MainApplication) getApplicationContext();

        fragmentsPagerAdapter = new FragmentsPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.pager);
        profile = findViewById(R.id.profile);
        wiki = findViewById(R.id.wiki);
        map = findViewById(R.id.map);
        chat = findViewById(R.id.chat);
        viewPager.setAdapter(fragmentsPagerAdapter);
        initilizeViews();
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);
        }else {
            app.setLastLocation(MainActivity.this);
        }
        app.initializeLocation();

    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
    private void initilizeViews(){
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
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                profile.setScaleX(0.8f);
                profile.setScaleY(0.8f);
                wiki.setScaleX(0.8f);
                wiki.setScaleY(0.8f);
                map.setScaleX(0.8f);
                map.setScaleY(0.8f);
                chat.setScaleX(0.8f);
                chat.setScaleY(0.8f);
                switch (position){
                    case 0:
                        profile.setScaleX(1f);
                        profile.setScaleY(1f);
                        profile.setColorFilter(getResources().getColor(R.color.icon_tint));
                        break;
                    case 1:
                        wiki.setScaleX(1f);
                        wiki.setScaleY(1f);
                        wiki.setColorFilter(getResources().getColor(R.color.icon_tint));
                        break;
                    case 2:
                        map.setScaleX(1f);
                        map.setScaleY(1f);
                        map.setColorFilter(getResources().getColor(R.color.icon_tint));
                        break;
                    case 3:
                        chat.setScaleX(1f);
                        chat.setScaleY(1f);
                        chat.setColorFilter(getResources().getColor(R.color.icon_tint));
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

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
                    fragment = new FriendsFragment();
                    break;
                default:
                    fragment = new Profile_Fragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
