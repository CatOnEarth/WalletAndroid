package com.snail.wallet.MainScreen.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "expenses")
public class Expenses extends Money{
    @ColumnInfo(name = "purpose")
    protected String purpose;
    @ColumnInfo(name = "category")
    protected String category;

    public Expenses(int id, double value, byte currency, String description) {
        super(id, value, currency, description);
    }
}
