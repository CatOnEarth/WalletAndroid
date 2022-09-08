package com.snail.wallet.MainScreen.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "money_source")
public class MoneySource {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "source")
    public String source;

    public MoneySource(int id, String source) {
        this.id = id;
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
