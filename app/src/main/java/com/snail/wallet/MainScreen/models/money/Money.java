package com.snail.wallet.MainScreen.models.money;


import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class Money {
    @PrimaryKey (autoGenerate=true)
    @ColumnInfo(name = "id")
    public int    id;
    @ColumnInfo(name = "value")
    public double value;
    @ColumnInfo(name = "currency")
    public int    currency;
    @ColumnInfo(name = "category")
    public int    category;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "description")
    public String description;

    public Money(int id, double value, int currency, int category, String date, String description) {
        this.id          = id;
        this.value       = value;
        this.currency    = currency;
        this.category    = category;
        this.date        = date;
        this.description = description;
    }

    @Ignore
    public Money(double value, int currency, int category, String date, String description) {
        this.value      = value;
        this.currency    = currency;
        this.category    = category;
        this.date        = date;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrencyType() {
        switch (this.currency) {
            case 0:
                return "руб";
            case 1:
                return "$";
            case 2:
                return "евро";
        }

        return "валюта";
    }
}
