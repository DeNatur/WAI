package com.erunetimeterror.wai;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

public class MainApplication extends MultiDexApplication {
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.context = getApplicationContext();

    }
}
