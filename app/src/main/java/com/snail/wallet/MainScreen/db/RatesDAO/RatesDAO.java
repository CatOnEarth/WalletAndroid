package com.snail.wallet.MainScreen.db.RatesDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.snail.wallet.MainScreen.models.money.Revenues;
import com.snail.wallet.MainScreen.models.parametrs.Rates;

@Dao
public interface RatesDAO {

    @Query("SELECT rate_to_rubel FROM rates WHERE type_currency = :type_currency")
    double getByTypeCurrency(int type_currency);

    @Insert
    long insert(Rates rate);

    @Update
    void update(Rates rate);

    @Delete
    void delete(Rates rate);
}
