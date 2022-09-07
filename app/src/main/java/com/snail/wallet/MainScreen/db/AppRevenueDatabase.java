package com.snail.wallet.MainScreen.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;
import com.snail.wallet.MainScreen.models.Revenues;

@Database(entities = {Revenues.class}, version = 1)
public abstract class AppRevenueDatabase extends RoomDatabase {
    public abstract RevenueDAO revenueDAO();
}