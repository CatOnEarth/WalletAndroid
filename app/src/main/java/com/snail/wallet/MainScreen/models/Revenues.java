package com.snail.wallet.MainScreen.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "revenues")
public class Revenues extends Money {
    @ColumnInfo(name = "storage_location")
    public String storage_location;
    @ColumnInfo(name = "source")
    public String source;
    @ColumnInfo(name = "category")
    public String category;


    public Revenues(double value, byte currency, String description) {
        super(value, currency, description);
    }

    public Revenues(Money money, String storage_location, String source, String category) {
        super(money.getValue(), money.getCurrency(), money.getDescription());

        this.storage_location = storage_location;
        this.source           = source;
        this.category         = category;
    }
}
