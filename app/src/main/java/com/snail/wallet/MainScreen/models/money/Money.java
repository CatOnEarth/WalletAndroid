package com.snail.wallet.MainScreen.models.money;


import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class Money {
    @PrimaryKey (autoGenerate=true)
    @ColumnInfo(name = "id")
    public long   id;
    @ColumnInfo(name = "value")
    public double value;
    @ColumnInfo(name = "type_currency")
    public int    type_currency;
    @ColumnInfo(name = "id_category")
    public long id_category;
    @ColumnInfo(name = "date_day")
    public int date_day;
    @ColumnInfo(name = "date_month")
    public int date_month;
    @ColumnInfo(name = "date_year")
    public int date_year;
    @ColumnInfo(name = "description")
    public String description;

    public Money(long id, double value, int type_currency, long id_category, int date_day,
                 int date_month, int date_year, String description) {
        this.id = id;
        this.value = value;
        this.type_currency = type_currency;
        this.id_category = id_category;
        this.date_day = date_day;
        this.date_month = date_month;
        this.date_year = date_year;
        this.description = description;
    }

    @Ignore
    public Money(double value, int type_currency, long id_category, int date_day,
                 int date_month, int date_year, String description) {
        this.value = value;
        this.type_currency = type_currency;
        this.id_category = id_category;
        this.date_day = date_day;
        this.date_month = date_month;
        this.date_year = date_year;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getType_currency() {
        return type_currency;
    }

    public void setType_currency(int type_currency) {
        this.type_currency = type_currency;
    }

    public long getId_category() {
        return id_category;
    }

    public void setId_category(long id_category) {
        this.id_category = id_category;
    }

    public String getDescription() {
        return description;
    }
}
