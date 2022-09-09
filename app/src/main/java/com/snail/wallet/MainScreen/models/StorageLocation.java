package com.snail.wallet.MainScreen.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "storage_location")
public class StorageLocation {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "location")
    public String location;

    public StorageLocation(int id, String location) {
        this.id = id;
        this.location = location;
    }

    @Ignore
    public StorageLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
