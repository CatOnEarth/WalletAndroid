package com.snail.wallet.MainScreen.db.CurrencyDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

import com.snail.wallet.MainScreen.models.Currency;

@Dao
public interface CurrencyDAO {
    @Query("SELECT * FROM currency")
    List<Currency> getAll();

    @Insert
    void insert(Currency currency);

    @Update
    void update(Currency currency);

    @Delete
    void delete(Currency currency);
}
