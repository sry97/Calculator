package com.example.myfirstapp;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by 荣耀 on 2017/9/12.
 */

public class ExampleApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}