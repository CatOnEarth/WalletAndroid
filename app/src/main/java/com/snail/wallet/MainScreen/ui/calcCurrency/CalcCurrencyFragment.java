package com.snail.wallet.MainScreen.ui.calcCurrency;

import static com.snail.wallet.WalletConstants.CODE_TYPE_CURRENCY_RUBLE;
import static com.snail.wallet.WalletConstants.CODE_TYPE_PARAM_CURRENCY;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.snail.wallet.CustomViews.SyncEditText.SyncEditText;
import com.snail.wallet.MainScreen.WalletActivity;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.CurrencyDAO.CurrencyDAO;
import com.snail.wallet.MainScreen.db.RatesDAO.RatesDAO;
import com.snail.wallet.MainScreen.models.parametrs.Currency;
import com.snail.wallet.MainScreen.models.retrofit.ExchangeRate;
import com.snail.wallet.MainScreen.retrofit.ExchangeRateService;
import com.snail.wallet.MainScreen.retrofit.RetrofitService;
import com.snail.wallet.MainScreen.ui.adapters.SpinnerAdapter;
import com.snail.wallet.databinding.FragmentCalcCurrencyBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalcCurrencyFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    private FragmentCalcCurrencyBinding binding;

    private Spinner spinnerCurrencyUp;
    private Spinner spinnerCurrencyDown;

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

        ImageButton bCountValueByExchangeRate = binding.imageButtonCountValueByExchangeRate;
        bCountValueByExchangeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Click button bCountValueByExchangeRate");

                countValueExchangeRate();
            }
        });
    }

    private void countValueExchangeRate() {
        Log.d(TAG, "countValueExchangeRate method");

        double input_val = 0;
        try {
           input_val =  Double.parseDouble(Objects.requireNonNull(editTextValueUp.getText()).toString());
        } catch (NumberFormatException e) {
            Log.e(TAG, "cannot parse input value of exchange rate to double");
            Toast.makeText(requireContext(), "Введено неверное число", Toast.LENGTH_LONG).show();

            return;
        }

        if (input_val < 0) {
            Toast.makeText(requireContext(), "Введите положительное число", Toast.LENGTH_LONG).show();
            return;
        }

        exchangeRate(input_val);
    }

    private void exchangeRate(double input_val) {
        AppDatabase db          = App.getInstance().getAppDatabase();
        CurrencyDAO currencyDAO = db.currencyDAO();
        RatesDAO ratesDAO       = db.ratesDAO();
        double output_val       = 0;
        DecimalFormat precision = new DecimalFormat("0.00");

        int currency_type_input  = ((Currency)spinnerCurrencyUp.getSelectedItem()).getType_currency();
        int currency_type_output = ((Currency)spinnerCurrencyDown.getSelectedItem()).getType_currency();

        if (currency_type_input == currency_type_output) {
            editTextValueDown.setText(precision.format(input_val));
            return;
        }

        if (currency_type_input != CODE_TYPE_CURRENCY_RUBLE) {
            output_val = input_val / ratesDAO.getByTypeCurrency(currency_type_input);
        } else {
            output_val = input_val;
        }

        if (currency_type_output != CODE_TYPE_CURRENCY_RUBLE) {
            output_val = output_val * ratesDAO.getByTypeCurrency(currency_type_output);
        }
        editTextValueDown.setText(precision.format(output_val));
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

        SpinnerAdapter spinnerCurrencyUpAdapter = new SpinnerAdapter(requireContext(), CODE_TYPE_PARAM_CURRENCY,
                currencyList);
        spinnerCurrencyUp.setAdapter(spinnerCurrencyUpAdapter);

        SpinnerAdapter spinnerCurrencyDownAdapter = new SpinnerAdapter(requireContext(), CODE_TYPE_PARAM_CURRENCY,
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