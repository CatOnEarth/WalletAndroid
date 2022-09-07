package com.snail.wallet.MainScreen.db;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {

    public static App instance;

    private AppRevenueDatabase revenue_database;
    private AppExpensesDatabase expenses_revenue;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        revenue_database = Room.databaseBuilder(this, AppRevenueDatabase.class, "database")
                .allowMainThreadQueries()
                .build();

        expenses_revenue = Room.databaseBuilder(this, AppExpensesDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppRevenueDatabase getRevenueDatabase() {
        return revenue_database;
    }

    public AppExpensesDatabase getExpensesDatabase() {
        return expenses_revenue;
    }
}