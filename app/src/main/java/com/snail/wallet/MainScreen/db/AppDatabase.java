package com.snail.wallet.MainScreen.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.snail.wallet.MainScreen.db.CategoryDAO.CategoryDAO;
import com.snail.wallet.MainScreen.db.CurrencyDAO.CurrencyDAO;
import com.snail.wallet.MainScreen.db.ExpensesDAO.ExpensesDAO;
import com.snail.wallet.MainScreen.db.MoneySouceDAO.MoneySourceDAO;
import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;
import com.snail.wallet.MainScreen.db.StorageLocationDAO.StorageLocationDAO;
import com.snail.wallet.MainScreen.models.Category;
import com.snail.wallet.MainScreen.models.Currency;
import com.snail.wallet.MainScreen.models.Expenses;
import com.snail.wallet.MainScreen.models.MoneySource;
import com.snail.wallet.MainScreen.models.Revenues;
import com.snail.wallet.MainScreen.models.StorageLocation;

@Database(entities = {Category.class, Currency.class, Expenses.class, MoneySource.class, Revenues.class, StorageLocation.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDAO categoryDAO();
    public abstract CurrencyDAO currencyDAO();
    public abstract ExpensesDAO expensesDAO();
    public abstract MoneySourceDAO moneySourceDAO();
    public abstract RevenueDAO revenueDAO();
    public abstract StorageLocationDAO storageLocationDAO();
}