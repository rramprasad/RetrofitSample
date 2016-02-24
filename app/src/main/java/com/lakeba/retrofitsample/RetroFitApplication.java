package com.lakeba.retrofitsample;

import android.app.Application;

import com.facebook.stetho.Stetho;


/**
 * @author Lakeba
 */
public class RetroFitApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

}
