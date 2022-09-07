package com.snail.wallet.MainScreen.db.ExpensesDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.snail.wallet.MainScreen.models.Expenses;

import java.util.List;

@Dao
public interface ExpensesDAO {
    @Query("SELECT * FROM expenses")
    List<Expenses> getAll();

    @Query("SELECT value FROM expenses")
    List<Double> getValues();

    @Insert
    void insert(Expenses expenses);

    @Update
    void update(Expenses expenses);

    @Delete
    void delete(Expenses expenses);
}
