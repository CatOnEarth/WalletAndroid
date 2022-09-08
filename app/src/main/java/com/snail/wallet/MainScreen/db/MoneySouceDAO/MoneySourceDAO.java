package com.snail.wallet.MainScreen.db.MoneySouceDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.snail.wallet.MainScreen.models.Category;
import com.snail.wallet.MainScreen.models.MoneySource;

import java.util.List;

@Dao
public interface MoneySourceDAO {
    @Query("SELECT * FROM money_source")
    List<MoneySource> getAll();

    @Insert
    void insert(MoneySource moneySource);

    @Update
    void update(MoneySource moneySource);

    @Delete
    void delete(MoneySource moneySource);
}