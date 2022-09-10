package com.snail.wallet.MainScreen.models.money;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "revenues")
public class Revenues extends Money {
    @ColumnInfo(name = "storage_location")
    public int storage_location;

    public Revenues(int id, double value, int currency, int category, String date, String description) {
        super(id, value, currency, category, date, description);
    }

    @Ignore
    public Revenues(double value, int currency, int category, String date,
                    String description, int storage_location) {
        super(value, currency, category, date, description);

        this.storage_location = storage_location;
    }

}