package com.github.kirivasile.videoservice;


import android.app.Application;

import com.github.kirivasile.videoservice.server.ConnectionService;
import com.github.kirivasile.videoservice.utils.VideoDB;

public class MyApplication extends Application {
    /*
    * Class for application itself. It is important for preventing repeatable initialization
    * of singletons
    */
    @Override
    public void onCreate() {
        super.onCreate();
        VideoDB.getInstance(getApplicationContext());
        ConnectionService.provideServide();
    }
}
