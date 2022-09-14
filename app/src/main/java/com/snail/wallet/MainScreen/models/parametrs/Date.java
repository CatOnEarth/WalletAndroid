package com.snail.wallet.MainScreen.models.parametrs;

import androidx.room.ColumnInfo;

public class Date {
    @ColumnInfo(name = "date_day")
    public int date_day;
    @ColumnInfo(name = "date_month")
    public int date_month;
    @ColumnInfo(name = "date_year")
    public int date_year;

    public Date(int date_day, int date_month, int date_year) {
        this.date_day = date_day;
        this.date_month = date_month;
        this.date_year = date_year;
    }

    public int getDate_day() {
        return date_day;
    }

    public int getDate_month() {
        return date_month;
    }

    public int getDate_year() {
        return date_year;
    }

    public String getDateString() {
        String res = "";
        if (date_day < 10) {
            res += "0";
        }
        res += date_day + ".";
        if (date_month < 10) {
            res += "0";
        }
        res += date_month + "." + date_year;

        return res;
    }
}
