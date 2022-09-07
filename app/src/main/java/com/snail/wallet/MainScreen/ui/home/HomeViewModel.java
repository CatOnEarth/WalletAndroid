package com.snail.wallet.MainScreen.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppExpensesDatabase;
import com.snail.wallet.MainScreen.db.AppRevenueDatabase;
import com.snail.wallet.MainScreen.db.ExpensesDAO.ExpensesDAO;
import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mTextRevenue;
    private final MutableLiveData<String> mTextExpenses;

    public HomeViewModel() {
        mTextRevenue  = new MutableLiveData<>();
        mTextExpenses = new MutableLiveData<>();

        mTextRevenue.setValue("Доходы: "   + CountRevenue());
        mTextExpenses.setValue("Расходы: " + CountExpenses());
    }

    private double CountRevenue() {
        AppRevenueDatabase db_revenue = App.getInstance().getRevenueDatabase();
        RevenueDAO revenueDAO         = db_revenue.revenueDAO();
        List<Double> revenueValues    = revenueDAO.getValues();

        double revenueSum = 0;
        for (int ii = 0; ii < revenueValues.size(); ++ii) {
            revenueSum += revenueValues.get(ii);
        }

        return revenueSum;
    }

    private double CountExpenses() {
        AppExpensesDatabase db_expenses = App.getInstance().getExpensesDatabase();
        ExpensesDAO expensesDAO         = db_expenses.expensesDAO();
        List<Double> expensesValues     = expensesDAO.getValues();

        double expensesSum = 0;
        for (int ii = 0; ii < expensesValues.size(); ++ii) {
            expensesSum += expensesValues.get(ii);
        }

        return expensesSum;
    }

    public LiveData<String> getTextRevenue() {
          return mTextRevenue;
    }
    public LiveData<String> getTextExpenses() {
        return mTextExpenses;
    }
}