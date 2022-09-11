package com.snail.wallet.MainScreen.models.parametrs;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "storage_location")
public class StorageLocation {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "id")
    public long    id;
    @ColumnInfo(name = "location")
    public String location;

    public StorageLocation(long id, String location) {
        this.id       = id;
        this.location = location;
    }

    @Ignore
    public StorageLocation(String location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
