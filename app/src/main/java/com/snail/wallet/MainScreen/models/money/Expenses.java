package com.snail.wallet.MainScreen.models.money;


import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "expenses")
public class Expenses extends Money {
    public Expenses(int id, double value, int currency, int category, int date_day, int date_month, int date_year, String description) {
        super(id, value, currency, category, date_day, date_month, date_year, description);
    }

    @Ignore
    public Expenses(double value, int currency, int category, int date_day, int date_month, int date_year, String description) {
        super(value, currency, category, date_day, date_month, date_year, description);
    }
}
