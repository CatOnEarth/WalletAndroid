package com.snail.wallet.MainScreen.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.snail.wallet.MainScreen.db.CategoryDAO.CategoryDAO;
import com.snail.wallet.MainScreen.db.CurrencyDAO.CurrencyDAO;
import com.snail.wallet.MainScreen.db.ExpensesDAO.ExpensesDAO;
import com.snail.wallet.MainScreen.db.RatesDAO.RatesDAO;
import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;
import com.snail.wallet.MainScreen.db.StorageLocationDAO.StorageLocationDAO;
import com.snail.wallet.MainScreen.models.parametrs.Category;
import com.snail.wallet.MainScreen.models.parametrs.Currency;
import com.snail.wallet.MainScreen.models.money.Expenses;
import com.snail.wallet.MainScreen.models.money.Revenues;
import com.snail.wallet.MainScreen.models.parametrs.Rates;
import com.snail.wallet.MainScreen.models.parametrs.StorageLocation;

@Database(entities = {Category.class, Currency.class, Expenses.class, Revenues.class,
        StorageLocation.class, Rates.class},
                      version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDAO        categoryDAO();
    public abstract CurrencyDAO        currencyDAO();
    public abstract ExpensesDAO        expensesDAO();
    public abstract RevenueDAO         revenueDAO();
    public abstract StorageLocationDAO storageLocationDAO();
    public abstract RatesDAO           ratesDAO();
}