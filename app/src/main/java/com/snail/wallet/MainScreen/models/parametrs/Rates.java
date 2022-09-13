package com.snail.wallet.MainScreen.models.parametrs;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "rates")
public class Rates {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "id")
    long id;

    @ColumnInfo(name = "type_currency")
    int type_currency;

    // Курс для рублю(1 руб = 0,016 USD)
    @ColumnInfo(name = "rate_to_rubel")
    double rate_to_rubel;

    public Rates(long id, int type_currency, double rate_to_rubel) {
        this.id = id;
        this.type_currency = type_currency;
        this.rate_to_rubel = rate_to_rubel;
    }

    @Ignore
    public Rates(int type_currency, double rate_to_rubel) {
        this.type_currency = type_currency;
        this.rate_to_rubel = rate_to_rubel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType_currency() {
        return type_currency;
    }

    public void setType_currency(int type_currency) {
        this.type_currency = type_currency;
    }

    public double getRate_to_rubel() {
        return rate_to_rubel;
    }
}
