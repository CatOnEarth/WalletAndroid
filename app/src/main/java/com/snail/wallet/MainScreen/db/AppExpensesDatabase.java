package com.snail.wallet.MainScreen.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.snail.wallet.MainScreen.db.ExpensesDAO.ExpensesDAO;
import com.snail.wallet.MainScreen.models.Expenses;

@Database(entities = {Expenses.class}, version = 1)
public abstract class AppExpensesDatabase extends RoomDatabase {
    public abstract ExpensesDAO expensesDAO();
}