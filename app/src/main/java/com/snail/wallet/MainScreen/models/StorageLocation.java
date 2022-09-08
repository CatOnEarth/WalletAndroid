package com.snail.wallet.MainScreen.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "storage_location")
public class StorageLocation {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "location")
    public String location;
}
