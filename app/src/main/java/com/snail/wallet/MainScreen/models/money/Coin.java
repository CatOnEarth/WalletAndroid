package com.snail.wallet.MainScreen.models.money;

import androidx.room.ColumnInfo;

public class Coin {
    @ColumnInfo(name = "value")
    public double value;
    @ColumnInfo(name = "type_currency")
    public int   type_currency;

    public Coin(double value, int type_currency) {
        this.value    = value;
        this.type_currency = type_currency;
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
}
