package com.example.vibeme;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Vibe_MePersistent_Data  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}