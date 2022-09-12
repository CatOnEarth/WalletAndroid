package com.snail.wallet.MainScreen.models.money;


import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "expenses")
public class Expenses extends Money {
    public Expenses(long id, double value, int type_currency, long id_category, int date_day, int date_month, int date_year, String description) {
        super(id, value, type_currency, id_category, date_day, date_month, date_year, description);
    }

    @Ignore
    public Expenses(double value, int type_currency, long id_category, int date_day, int date_month, int date_year, String description) {
        super(value, type_currency, id_category, date_day, date_month, date_year, description);
    }
}
