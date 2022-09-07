package com.snail.wallet.MainScreen.db.ExpensesDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.snail.wallet.MainScreen.models.Coin;
import com.snail.wallet.MainScreen.models.Expenses;
import com.snail.wallet.MainScreen.models.Money;

import java.util.List;

@Dao
public interface ExpensesDAO {
    @Query("SELECT * FROM expenses")
    List<Expenses> getAll();

    @Query("SELECT id, value, currency, category, description FROM expenses")
    List<Money> getValues();

    @Query("SELECT value, currency FROM expenses")
    List<Coin> getSum();

    @Insert
    void insert(Expenses expenses);

    @Update
    void update(Expenses expenses);

    @Delete
    void delete(Expenses expenses);
}
