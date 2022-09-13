package com.snail.wallet.MainScreen.ui.calcCurrency;

import static com.snail.wallet.WalletConstants.CODE_TYPE_PARAM_CURRENCY;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.snail.wallet.CustomViews.SyncEditText.SyncEditText;
import com.snail.wallet.MainScreen.WalletActivity;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.CurrencyDAO.CurrencyDAO;
import com.snail.wallet.MainScreen.models.parametrs.Currency;
import com.snail.wallet.MainScreen.models.retrofit.ExchangeRate;
import com.snail.wallet.MainScreen.retrofit.ExchangeRateService;
import com.snail.wallet.MainScreen.retrofit.RetrofitService;
import com.snail.wallet.MainScreen.ui.adapters.SpinnerAdapter;
import com.snail.wallet.databinding.FragmentCalcCurrencyBinding;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalcCurrencyFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    private FragmentCalcCurrencyBinding binding;

    private Spinner spinnerCurrencyUp;
    private Spinner spinnerCurrencyDown;

    private SpinnerAdapter spinnerCurrencyUpAdapter;
    private SpinnerAdapter spinnerCurrencyDownAdapter;

    private SyncEditText editTextValueUp;
    private SyncEditText editTextValueDown;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView method");

        binding = FragmentCalcCurrencyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initViews();
        initSpinnersAdapters();

        return root;
    }

    private void initViews() {
        Log.d(TAG, "initViews method");

        spinnerCurrencyUp   = binding.spinnerExchangeCurrencyUp;
        spinnerCurrencyDown = binding.spinnerExchangeDown;
        editTextValueUp     = binding.editTextNumberValueExchangeUp;
        editTextValueDown   = binding.editTextNumberValueExchangeDown;

        Button bUpdateExchange = binding.buttonUpdateExchangeCalcCurrencyFragment;
        bUpdateExchange.setOnClickListener(view -> {
            Log.i(TAG, "Click button exchange update");
            updateExchange();
        });

        initEditTextSync();
    }

    private void initEditTextSync() {
        editTextValueUp.setDependencies(editTextValueDown);
        editTextValueDown.setDependencies(editTextValueUp);
    }

    private void updateExchange() {
        Log.d(TAG, "updateExchange method");

        getExchangeRate();
    }

    private void initSpinnersAdapters() {
        Log.d(TAG, "initSpinnersAdapters method");

        AppDatabase db          = App.getInstance().getAppDatabase();
        CurrencyDAO currencyDAO = db.currencyDAO();

        ArrayList<Currency> currencyList = (ArrayList<Currency>) currencyDAO.getAll();

        spinnerCurrencyUpAdapter = new SpinnerAdapter(requireContext(), CODE_TYPE_PARAM_CURRENCY,
                currencyList);
        spinnerCurrencyUp.setAdapter(spinnerCurrencyUpAdapter);

        spinnerCurrencyDownAdapter = new SpinnerAdapter(requireContext(), CODE_TYPE_PARAM_CURRENCY,
                currencyList);
        spinnerCurrencyDown.setAdapter(spinnerCurrencyDownAdapter);
    }

    public void getExchangeRate() {
        ((WalletActivity)requireActivity()).initRates();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView method");

        super.onDestroyView();
        binding = null;
    }
}