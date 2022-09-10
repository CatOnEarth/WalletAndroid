package com.snail.wallet.MainScreen.models.money;


import androidx.room.Entity;

@Entity(tableName = "expenses")
public class Expenses extends Money{
    public Expenses(int id, double value, int currency, int category, String date, String description) {
        super(id, value, currency, category, date, description);
    }
}
