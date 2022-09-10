package com.snail.wallet.MainScreen.models.money;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "revenues")
public class Revenues extends Money {
    @ColumnInfo(name = "storage_location")
    public int storage_location;

    public Revenues(int id, double value, int currency, int category, int date_day, int date_month, int date_year, String description) {
        super(id, value, currency, category, date_day, date_month, date_year, description);
    }

    @Ignore
    public Revenues(double value, int currency, int category, int date_day, int date_month, int date_year, String description, int storage_location) {
        super(value, currency, category, date_day, date_month, date_year, description);
        this.storage_location = storage_location;
    }

    public int getStorage_location() {
        return storage_location;
    }
}
