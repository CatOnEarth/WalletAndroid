package com.snail.wallet.MainScreen.ui.expenses;

import static com.snail.wallet.WalletConstants.ADDING_OBJECT_TYPE;
import static com.snail.wallet.WalletConstants.ADDING_OBJ_EXPENSES_TYPE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.snail.wallet.MainScreen.activities.AddActivity;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.ExpensesDAO.ExpensesDAO;
import com.snail.wallet.MainScreen.models.money.Expenses;
import com.snail.wallet.MainScreen.ui.adapters.RecyclerViewAdapter;
import com.snail.wallet.databinding.FragmentExpensesBinding;

import java.util.ArrayList;
import java.util.List;

public class ExpensesFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    private FragmentExpensesBinding binding;

    private RecyclerView        recyclerViewExpenses;
    private RecyclerViewAdapter recyclerViewAdapter;

    private ExpensesDAO    expensesDAO;
    private List<Expenses> expenses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreate method");

        binding = FragmentExpensesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initViews();
        initRecyclerViewExpenses();

        return root;
    }

    private void initViews() {
        Log.d(TAG, "initViews method");

        recyclerViewExpenses = binding.recyclerViewExpenses;

        FloatingActionButton bAddRevenue = binding.floatingActionButtonExpenses;
        bAddRevenue.setOnClickListener(view -> {
            Log.i(TAG, "floating button add expenses clicked");
            StartAddActivity();
        });
    }

    private void initRecyclerViewExpenses() {
        Log.d(TAG, "initRecyclerViewExpenses method");

        AppDatabase db = App.getInstance().getAppDatabase();
        expensesDAO    = db.expensesDAO();

        expenses = new ArrayList<>();
        expenses.addAll(expensesDAO.getAll());
        recyclerViewAdapter = new RecyclerViewAdapter(ADDING_OBJ_EXPENSES_TYPE, expenses, getContext());

        recyclerViewExpenses.setAdapter(recyclerViewAdapter);

        recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void StartAddActivity() {
        Log.d(TAG, "StartAddActivity method");

        Intent intent = new Intent(getContext(), AddActivity.class);
        intent.putExtra(ADDING_OBJECT_TYPE, ADDING_OBJ_EXPENSES_TYPE);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume method");
        super.onResume();

        updateExpensesAdapter();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateExpensesAdapter() {
        Log.d(TAG, "updateExpensesAdapter method");

        expenses.clear();
        expenses.addAll(expensesDAO.getAll());
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView method");
        super.onDestroyView();
        binding = null;
    }
}