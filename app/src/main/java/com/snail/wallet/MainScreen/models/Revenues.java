package com.snail.wallet.MainScreen.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "revenues")
public class Revenues extends Money {
    @ColumnInfo(name = "storage_location")
    public String storage_location;
    @ColumnInfo(name = "source")
    public String source;

    public Revenues(int id, double value, int currency, int category, String description) {
        super(id, value, currency, category, description);
    }
}
