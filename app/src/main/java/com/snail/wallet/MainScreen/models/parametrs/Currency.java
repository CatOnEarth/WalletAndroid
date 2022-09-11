package com.snail.wallet.MainScreen.models.parametrs;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "currency")
public class Currency {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "id")
    public long    id;
    @ColumnInfo(name = "type_currency")
    public int    type_currency;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "symbol")
    public String symbol;

    public Currency(long id, int type_currency, String name, String symbol) {
        this.id     = id;
        this.type_currency   = type_currency;
        this.name   = name;
        this.symbol = symbol;
    }

    @Ignore
    public Currency(int type_currency, String name, String symbol) {
        this.type_currency   = type_currency;
        this.name   = name;
        this.symbol = symbol;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }
}
