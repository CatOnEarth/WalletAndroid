package com.snail.wallet.MainScreen.db.ExpensesDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.snail.wallet.MainScreen.models.money.Coin;
import com.snail.wallet.MainScreen.models.money.Expenses;
import com.snail.wallet.MainScreen.models.parametrs.Date;

import java.util.List;

@Dao
public interface ExpensesDAO {
    @Query("SELECT * FROM expenses")
    List<Expenses> getAll();

    @Query("SELECT value, currency FROM expenses")
    List<Coin> getValues();

    @Query("SELECT * FROM expenses WHERE id = :id")
    Expenses getById(int id);

    @Query("SELECT date_day, date_month, date_year FROM expenses WHERE id = :id")
    Date getDateById(int id);

    @Insert
    void insert(Expenses expenses);

    @Update
    void update(Expenses expenses);

    @Delete
    void delete(Expenses expenses);

    @Query("DELETE FROM expenses WHERE id = :id")
    void deleteById(int id);
}
