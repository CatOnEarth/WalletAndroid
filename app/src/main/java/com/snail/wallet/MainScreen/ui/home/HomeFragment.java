package com.snail.wallet.MainScreen.ui.home;

import static com.snail.wallet.WalletConstants.ADDING_OBJECT_TYPE;
import static com.snail.wallet.WalletConstants.ADDING_OBJ_EXPENSES_TYPE;
import static com.snail.wallet.WalletConstants.ADDING_OBJ_REVENUE_TYPE;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CURRENCY_DOLLAR;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CURRENCY_EURO;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CURRENCY_RUBLE;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CURRENCY_TURKISH_LIRA;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.snail.wallet.MainScreen.activities.AddActivity;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.ExpensesDAO.ExpensesDAO;
import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;
import com.snail.wallet.MainScreen.models.money.Coin;
import com.snail.wallet.R;
import com.snail.wallet.databinding.FragmentHomeBinding;

import java.text.DecimalFormat;
import java.util.List;

public class HomeFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    private FragmentHomeBinding binding;

    private TextView textViewRevenueRubles;
    private TextView textViewRevenueDollars;
    private TextView textViewRevenueEuro;
    private TextView textViewRevenueLira;

    private TextView textViewExpensesRubles;
    private TextView textViewExpensesDollars;
    private TextView textViewExpensesEuro;
    private TextView textViewExpensesLira;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreate method");

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initFindView(root);
        initData();

        initButtons(root);

        return root;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume method");

        super.onResume();
        initData();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView method");

        super.onDestroyView();
        binding = null;
    }

    private void initButtons(View root) {
        Log.d(TAG, "initButtons method");

        ImageButton bAddRevenue  = root.findViewById(R.id.buttonHomeFragmentAddRevenue);
        ImageButton bAddExpenses = root.findViewById(R.id.buttonHomeFragmentAddExpenses);

        bAddRevenue.setOnClickListener(view -> startAddActivity(ADDING_OBJ_REVENUE_TYPE));

        bAddExpenses.setOnClickListener(view -> startAddActivity(ADDING_OBJ_EXPENSES_TYPE));
    }

    private void startAddActivity(int type_add) {
        Log.d(TAG, "startAddActivity method");

        Intent intent = new Intent(getContext(), AddActivity.class);
        intent.putExtra(ADDING_OBJECT_TYPE, type_add);
        requireContext().startActivity(intent);
    }

    private void initFindView(View root) {
        Log.d(TAG, "initFindView method");

        textViewRevenueRubles   = root.findViewById(R.id.textViewHomeFragmentRevenueDisplayRubles);
        textViewRevenueDollars  = root.findViewById(R.id.textViewHomeFragmentRevenueDisplayDollars);
        textViewRevenueEuro     = root.findViewById(R.id.textViewHomeFragmentRevenueDisplayEuro);
        textViewRevenueLira     = root.findViewById(R.id.textViewHomeFragmentRevenueDisplayLira);

        textViewExpensesRubles  = root.findViewById(R.id.textViewHomeFragmentExpensesDisplayRubles);
        textViewExpensesDollars = root.findViewById(R.id.textViewHomeFragmentExpensesDisplayDollars);
        textViewExpensesEuro    = root.findViewById(R.id.textViewHomeFragmentExpensesDisplayEuro);
        textViewExpensesLira    = root.findViewById(R.id.textViewHomeFragmentExpensesDisplayLira);
    }

    private void initData() {
        Log.d(TAG, "initData method");

        AppDatabase db          = App.getInstance().getAppDatabase();

        RevenueDAO revenueDAO   = db.revenueDAO();
        ExpensesDAO expensesDAO = db.expensesDAO();
        List<Coin> revenues     = revenueDAO.getValues();
        List<Coin> expenses     = expensesDAO.getValues();

        double[] revenue_data  = countMoneyData(revenues);
        double[] expenses_data = countMoneyData(expenses);

        DecimalFormat precision = new DecimalFormat("0.00");

        initDataRevenue(revenue_data, precision);
        initDataExpenses(expenses_data, precision);
    }

    private double[] countMoneyData(List<Coin> coins) {
        Log.d(TAG, "countMoneyData method");

        double rubles       = 0;
        double dollars      = 0;
        double euro         = 0;
        double turk_lira    = 0;

        Coin temp;
        for (int ii = 0; ii < coins.size(); ++ii) {
            temp = coins.get(ii);

            if        (temp.getType_currency() == CODE_TYPE_CURRENCY_RUBLE) {
                rubles      += temp.getValue();
            } else if (temp.getType_currency() == CODE_TYPE_CURRENCY_DOLLAR) {
                dollars     += temp.getValue();
            } else if (temp.getType_currency() == CODE_TYPE_CURRENCY_EURO) {
                euro        += temp.getValue();
            } else if (temp.getType_currency() == CODE_TYPE_CURRENCY_TURKISH_LIRA) {
                turk_lira   += temp.getValue();
            }
        }

        return new double[] {rubles, dollars, euro, turk_lira};
    }

    private void initDataRevenue(double[] money, DecimalFormat precision) {
        Log.d(TAG, "initDataRevenue method");

        String rubles_str  = precision.format(money[CODE_TYPE_CURRENCY_RUBLE]) + "₽";
        String dollars_str = precision.format(money[CODE_TYPE_CURRENCY_DOLLAR]) + "$";
        String euro_str    = precision.format(money[CODE_TYPE_CURRENCY_EURO]) + "€";
        String lira_str    = precision.format(money[CODE_TYPE_CURRENCY_TURKISH_LIRA]) + "₺";

        textViewRevenueRubles.setText(rubles_str);
        textViewRevenueDollars.setText(dollars_str);
        textViewRevenueEuro.setText(euro_str);
        textViewRevenueLira.setText(lira_str);
    }

    private void initDataExpenses(double[] money, DecimalFormat precision) {
        Log.d(TAG, "initDataExpenses method");

        String rubles_str  = precision.format(money[CODE_TYPE_CURRENCY_RUBLE]) + "₽";
        String dollars_str = precision.format(money[CODE_TYPE_CURRENCY_DOLLAR]) + "$";
        String euro_str    = precision.format(money[CODE_TYPE_CURRENCY_EURO]) + "€";
        String lira_str    = precision.format(money[CODE_TYPE_CURRENCY_TURKISH_LIRA]) + "₺";

        textViewExpensesRubles.setText(rubles_str);
        textViewExpensesDollars.setText(dollars_str);
        textViewExpensesEuro.setText(euro_str);
        textViewExpensesLira.setText(lira_str);
    }
}
