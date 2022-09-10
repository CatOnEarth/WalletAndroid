package com.snail.wallet.MainScreen.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.snail.wallet.MainScreen.WalletActivity;
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
import java.util.Objects;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    TextView textViewRevenueRubles;
    TextView textViewRevenueDollars;
    TextView textViewRevenueEuro;
    TextView textViewRevenueLira;

    TextView textViewExpensesRubles;
    TextView textViewExpensesDollars;
    TextView textViewExpensesEuro;
    TextView textViewExpensesLira;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initFindView(root);
        initData();

        initButtons(root);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initButtons(View root) {
        ImageButton bAddRevenue  = root.findViewById(R.id.bAddRevenuesHome);
        ImageButton bAddExpenses = root.findViewById(R.id.bAddExpensesHome);

        bAddRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddActivity(AddActivity.ADDING_REVENUE);
            }
        });

        bAddExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddActivity(AddActivity.ADDING_EXPENSES);
            }
        });
    }

    private void startAddActivity(int type_add) {
        Intent intent = new Intent(getContext(), AddActivity.class);
        intent.putExtra(AddActivity.ADDING_OBJECT, type_add);
        requireContext().startActivity(intent);
    }

    private void initFindView(View root) {
        textViewRevenueRubles   = root.findViewById(R.id.textViewHomeRevenueDisplayRubles);
        textViewRevenueDollars  = root.findViewById(R.id.textViewHomeRevenueDisplayDollars);
        textViewRevenueEuro     = root.findViewById(R.id.textViewHomeRevenueDisplayEuro);
        textViewRevenueLira     = root.findViewById(R.id.textViewHomeRevenueDisplayLira);

        textViewExpensesRubles  = root.findViewById(R.id.textViewHomeExpensesDisplayRubles);
        textViewExpensesDollars = root.findViewById(R.id.textViewHomeExpensesDisplayDollars);
        textViewExpensesEuro    = root.findViewById(R.id.textViewHomeExpensesDisplayEuro);
        textViewExpensesLira    = root.findViewById(R.id.textViewHomeExpensesDisplayLira);
    }

    private void initData() {
        AppDatabase db = App.getInstance().getAppDatabase();

        initDataRevenues(db);
        initDataExpenses(db);
    }

    private void initDataRevenues(AppDatabase db) {
        RevenueDAO revenueDAO = db.revenueDAO();
        List<Coin> revenues   = revenueDAO.getValues();

        double rubles  = 0;
        double dollars = 0;
        double euro    = 0;
        double lira    = 0;

        Coin temp;
        for (int ii = 0; ii < revenues.size(); ++ii) {
            temp = revenues.get(ii);

            if (temp.getCurrency() == WalletActivity.CODE_CURRENCY_RUBLE) {
                rubles += temp.getValue();
            } else if (temp.getCurrency() == WalletActivity.CODE_CURRENCY_DOLLAR) {
                dollars += temp.getValue();
            } else if (temp.getCurrency() == WalletActivity.CODE_CURRENCY_EURO) {
                euro += temp.getValue();
            } else if (temp.getCurrency() == WalletActivity.CODE_CURRENCY_TURKISH_LIRA) {
                lira += temp.getValue();
            }
        }

        DecimalFormat precision = new DecimalFormat("0.00");

        String rubles_str  = precision.format(rubles) + "₽";
        String dollars_str = precision.format(dollars) + "$";
        String euro_str    = precision.format(euro) + "€";
        String lira_str    = precision.format(lira) + "₺";

        textViewRevenueRubles.setText(rubles_str);
        textViewRevenueDollars.setText(dollars_str);
        textViewRevenueEuro.setText(euro_str);
        textViewRevenueLira.setText(lira_str);
    }

    private void initDataExpenses(AppDatabase db) {
        ExpensesDAO expensesDAO = db.expensesDAO();
        List<Coin> expenses     = expensesDAO.getValues();

        double rubles  = 0;
        double dollars = 0;
        double euro    = 0;
        double lira    = 0;

        Coin temp;
        for (int ii = 0; ii < expenses.size(); ++ii) {
            temp = expenses.get(ii);

            if (temp.getCurrency() == WalletActivity.CODE_CURRENCY_RUBLE) {
                rubles += temp.getValue();
            } else if (temp.getCurrency() == WalletActivity.CODE_CURRENCY_DOLLAR) {
                dollars += temp.getValue();
            } else if (temp.getCurrency() == WalletActivity.CODE_CURRENCY_EURO) {
                euro += temp.getValue();
            } else if (temp.getCurrency() == WalletActivity.CODE_CURRENCY_TURKISH_LIRA) {
                lira += temp.getValue();
            }
        }

        DecimalFormat precision = new DecimalFormat("0.00");

        String rubles_str  = precision.format(rubles) + "₽";
        String dollars_str = precision.format(dollars) + "$";
        String euro_str    = precision.format(euro) + "€";
        String lira_str    = precision.format(lira) + "₺";

        textViewExpensesRubles.setText(rubles_str);
        textViewExpensesDollars.setText(dollars_str);
        textViewExpensesEuro.setText(euro_str);
        textViewExpensesLira.setText(lira_str);
    }

}