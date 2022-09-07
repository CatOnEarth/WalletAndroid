package com.snail.wallet.MainScreen.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "expenses")
public class Expenses extends Money{
    @ColumnInfo(name = "purpose")
    public String purpose;
    @ColumnInfo(name = "category")
    public String category;

    public Expenses(double value, byte currency, String description) {
        super(value, currency, description);
    }
}
