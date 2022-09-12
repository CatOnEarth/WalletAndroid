package com.snail.wallet.MainScreen.db.RevenueDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.snail.wallet.MainScreen.models.money.Coin;
import com.snail.wallet.MainScreen.models.money.Revenues;
import com.snail.wallet.MainScreen.models.parametrs.Date;

import java.util.List;

@Dao
public interface RevenueDAO {
    @Query("SELECT * FROM revenues")
    List<Revenues> getAll();

    @Query("SELECT value, type_currency FROM revenues")
    List<Coin> getValues();

    @Query("SELECT * FROM revenues WHERE id = :id")
    Revenues getById(long id);

    @Query("SELECT date_day, date_month, date_year FROM revenues WHERE id = :id")
    Date getDateById(long id);

    @Insert
    long insert(Revenues revenue);

    @Update
    void update(Revenues revenue);

    @Delete
    void delete(Revenues revenue);

    @Query("DELETE FROM revenues WHERE id_category = :id_category")
    void deleteByIdCategory(long id_category);

    @Query("DELETE FROM revenues WHERE storage_location = :storage_location")
    void deleteByStorageLocation(long storage_location);

    @Query("DELETE FROM revenues WHERE id = :id")
    void deleteById(long id);
}
