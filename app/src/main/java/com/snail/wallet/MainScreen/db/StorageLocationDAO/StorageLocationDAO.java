package com.snail.wallet.MainScreen.db.StorageLocationDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.snail.wallet.MainScreen.models.parametrs.StorageLocation;

import java.util.List;

@Dao
public interface StorageLocationDAO {
    @Query("SELECT * FROM storage_location")
    List<StorageLocation> getAll();

    @Query("SELECT * FROM storage_location WHERE location = :location")
    List<StorageLocation> getByStorageLocationName(String location);

    @Query("SELECT location FROM storage_location WHERE id = :id")
    String getLocationById(int id);

    @Insert
    void insert(StorageLocation storage_location);

    @Update
    void update(StorageLocation storage_location);

    @Delete
    void delete(StorageLocation storage_location);
}