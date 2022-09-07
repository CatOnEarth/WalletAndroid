package com.snail.wallet.MainScreen.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "revenues")
public class Revenues extends Money {
    @ColumnInfo(name = "storage_location")
    protected String storage_location;
    @ColumnInfo(name = "source")
    protected String source;
    @ColumnInfo(name = "category")
    protected String category;


    public Revenues(int id, double value, byte currency, String description) {
        super(id, value, currency, description);
    }

    public Revenues(Money money, String storage_location, String source, String category) {
        super(money.getId(), money.getValue(), money.getCurrency(), money.getDescription());

        this.storage_location = storage_location;
        this.source           = source;
        this.category         = category;
    }
}
