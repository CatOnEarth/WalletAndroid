package com.snail.wallet.MainScreen.models;

import androidx.room.ColumnInfo;

public class Coin {
    @ColumnInfo(name = "value")
    public double value;
    @ColumnInfo(name = "currency")
    public int   currency;

    public Coin(double value, int currency) {
        this.value = value;
        this.currency = currency;
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
}
