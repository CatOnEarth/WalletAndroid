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

    private RecyclerViewAdapter recyclerViewAdapter;

    private ExpensesDAO    expensesDAO;
    private List<Expenses> expenses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentExpensesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerViewExpenses;

        AppDatabase db = App.getInstance().getAppDatabase();
        expensesDAO = db.expensesDAO();

        expenses = new ArrayList<>();
        expenses.addAll(expensesDAO.getAll());
        recyclerViewAdapter = new RecyclerViewAdapter(ADDING_OBJ_EXPENSES_TYPE, expenses, getContext());

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
        intent.putExtra(ADDING_OBJECT_TYPE, ADDING_OBJ_EXPENSES_TYPE);
        startActivity(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume Fragment");
        expenses.clear();
        expenses.addAll(expensesDAO.getAll());
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}