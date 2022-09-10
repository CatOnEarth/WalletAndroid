package com.snail.wallet.MainScreen.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.ExpensesDAO.ExpensesDAO;
import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;
import com.snail.wallet.MainScreen.models.money.Coin;

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
        AppDatabase db           = App.getInstance().getAppDatabase();
        RevenueDAO revenueDAO    = db.revenueDAO();
        List<Coin> revenueValues = revenueDAO.getSum();

        double revenueSum = 0;
        for (int ii = 0; ii < revenueValues.size(); ++ii) {
            revenueSum += revenueValues.get(ii).getValue();
        }

        return revenueSum;
    }

    private double CountExpenses() {
        AppDatabase db_expenses   = App.getInstance().getAppDatabase();
        ExpensesDAO expensesDAO   = db_expenses.expensesDAO();
        List<Coin> expensesValues = expensesDAO.getSum();

        double expensesSum = 0;
        for (int ii = 0; ii < expensesValues.size(); ++ii) {
            expensesSum += expensesValues.get(ii).getValue();
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