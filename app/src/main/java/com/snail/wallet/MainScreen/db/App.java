package com.snail.wallet.MainScreen.db;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {

    public static App instance;
    public static final String DATABASE_NAME = "wallet_db";

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        database = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getAppDatabase() {
        return database;
    }

}