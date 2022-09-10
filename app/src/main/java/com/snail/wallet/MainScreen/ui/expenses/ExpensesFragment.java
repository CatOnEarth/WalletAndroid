package com.snail.wallet.MainScreen.ui.expenses;

import static com.snail.wallet.MainScreen.activities.AddActivity.ADDING_EXPENSES;

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
import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;
import com.snail.wallet.MainScreen.models.money.Expenses;
import com.snail.wallet.MainScreen.models.money.Revenues;
import com.snail.wallet.MainScreen.ui.adapters.RecyclerViewAdapter;
import com.snail.wallet.databinding.FragmentExpensesBinding;

import java.util.ArrayList;
import java.util.List;

public class ExpensesFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    private FragmentExpensesBinding binding;

    private RecyclerView        recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private ExpensesDAO    expensesDAO;
    private AppDatabase    db;
    private List<Expenses> expenses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentExpensesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerViewExpenses;

        db          = App.getInstance().getAppDatabase();
        expensesDAO = db.expensesDAO();

        expenses = new ArrayList<>();
        expenses.addAll(expensesDAO.getAll());
        recyclerViewAdapter = new RecyclerViewAdapter(ADDING_EXPENSES, expenses, getContext());

        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton bAddRevenue = binding.floatingActionButtonExpenses;
        bAddRevenue.setOnClickListener(view -> {
            Log.i(TAG, "floating button clicked");
            StartAddActivity();
        });

        return root;
    }

    private void StartAddActivity() {
        Intent intent = new Intent(getContext(), AddActivity.class);
        intent.putExtra(AddActivity.ADDING_OBJECT, ADDING_EXPENSES);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume Fragment");
        expenses.clear();
        expenses.addAll(expensesDAO.getAll());
        recyclerViewAdapter.notifyItemRangeChanged(0, expenses.size());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}