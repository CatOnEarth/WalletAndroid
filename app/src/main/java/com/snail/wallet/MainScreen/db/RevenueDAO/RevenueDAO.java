package com.snail.wallet.MainScreen.db.RevenueDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.snail.wallet.MainScreen.models.Coin;
import com.snail.wallet.MainScreen.models.Money;
import com.snail.wallet.MainScreen.models.Revenues;

import java.util.List;

@Dao
public interface RevenueDAO {
    @Query("SELECT * FROM revenues")
    List<Revenues> getAll();

    @Query("SELECT id, value, currency, category, description FROM revenues")
    List<Money> getValues();

    @Query("SELECT value, currency FROM revenues")
    List<Coin> getSum();

    @Insert
    void insert(Revenues revenue);

    @Update
    void update(Revenues revenue);

    @Delete
    void delete(Revenues revenue);
}
