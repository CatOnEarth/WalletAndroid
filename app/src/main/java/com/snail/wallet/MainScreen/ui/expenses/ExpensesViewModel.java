package com.snail.wallet.MainScreen.ui.expenses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExpensesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ExpensesViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}