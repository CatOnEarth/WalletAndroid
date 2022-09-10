package com.snail.wallet.MainScreen.models.parametrs;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "currency")
public class Currency {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "id")
    public int    id;
    @ColumnInfo(name = "type")
    public int    type;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "symbol")
    public String symbol;

    public Currency(int id, int type, String name, String symbol) {
        this.id     = id;
        this.type   = type;
        this.name   = name;
        this.symbol = symbol;
    }

    @Ignore
    public Currency(int type, String name, String symbol) {
        this.type   = type;
        this.name   = name;
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
