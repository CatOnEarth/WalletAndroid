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
    @ColumnInfo(name = "date_day")
    public int date_day;
    @ColumnInfo(name = "date_month")
    public int date_month;
    @ColumnInfo(name = "date_year")
    public int date_year;
    @ColumnInfo(name = "description")
    public String description;

    public Money(int id, double value, int currency, int category, int date_day,
                 int date_month, int date_year, String description) {
        this.id = id;
        this.value = value;
        this.currency = currency;
        this.category = category;
        this.date_day = date_day;
        this.date_month = date_month;
        this.date_year = date_year;
        this.description = description;
    }

    @Ignore
    public Money(double value, int currency, int category, int date_day,
                 int date_month, int date_year, String description) {
        this.value = value;
        this.currency = currency;
        this.category = category;
        this.date_day = date_day;
        this.date_month = date_month;
        this.date_year = date_year;
        this.description = description;
    }

    public int getDate_day() {
        return date_day;
    }

    public void setDate_day(int date_day) {
        this.date_day = date_day;
    }

    public int getDate_month() {
        return date_month;
    }

    public void setDate_month(int date_month) {
        this.date_month = date_month;
    }

    public int getDate_year() {
        return date_year;
    }

    public void setDate_year(int date_year) {
        this.date_year = date_year;
    }

    @Ignore




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
