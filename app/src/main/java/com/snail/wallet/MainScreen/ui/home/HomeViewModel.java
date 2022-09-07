package com.snail.wallet.MainScreen.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mTextRevenue;
    private final MutableLiveData<String> mTextExpenses;

    public HomeViewModel() {
        mTextRevenue = new MutableLiveData<>();
        mTextRevenue.setValue("Доходы: ");

        mTextExpenses = new MutableLiveData<>();
        mTextExpenses.setValue("Расходы: ");

    }

    public LiveData<String> getTextRevenue() {
        return mTextRevenue;
    }
    public LiveData<String> getTextExpenses() {
        return mTextExpenses;
    }
}