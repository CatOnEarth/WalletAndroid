package com.snail.wallet.MainScreen.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "expenses")
public class Expenses extends Money{
    @ColumnInfo(name = "purpose")
    public String purpose;

    public Expenses(int id, double value, int currency, int category, String description) {
        super(id, value, currency, category, description);
    }
}
