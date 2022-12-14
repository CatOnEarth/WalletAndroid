package com.snail.wallet.MainScreen.models.parametrs;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "id")
    public long   id;
    @ColumnInfo(name = "type")
    public int   type;
    @ColumnInfo(name = "name")
    public String name;

    public Category(long id, int type, String name) {
        this.id  = id;
        this.type = type;
        this.name = name;
    }

    @Ignore
    public Category(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
