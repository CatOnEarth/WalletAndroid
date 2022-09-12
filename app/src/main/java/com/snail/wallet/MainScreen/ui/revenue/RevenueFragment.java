package com.snail.wallet.MainScreen.ui.revenue;


import static com.snail.wallet.WalletConstants.ADDING_OBJECT_TYPE;
import static com.snail.wallet.WalletConstants.ADDING_OBJ_REVENUE_TYPE;

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
import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;
import com.snail.wallet.MainScreen.models.money.Revenues;
import com.snail.wallet.MainScreen.ui.adapters.RecyclerViewAdapter;
import com.snail.wallet.databinding.FragmentRevenueBinding;

import java.util.ArrayList;
import java.util.List;

public class RevenueFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    private FragmentRevenueBinding binding;

    private RecyclerView        recyclerViewRevenue;
    private RecyclerViewAdapter recyclerViewAdapter;

    private RevenueDAO     revenueDAO;
    private List<Revenues> revenues;

    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView method");

        binding = FragmentRevenueBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initViews();
        initRecyclerViewRevenue();

        return root;
    }

    private void initRecyclerViewRevenue() {
        Log.d(TAG, "initRecyclerViewRevenue method");

        AppDatabase db = App.getInstance().getAppDatabase();
        revenueDAO     = db.revenueDAO();

        revenues = new ArrayList<>();
        revenues.addAll(revenueDAO.getAll());
        recyclerViewAdapter = new RecyclerViewAdapter(ADDING_OBJ_REVENUE_TYPE, revenues, getContext());

        recyclerViewRevenue.setAdapter(recyclerViewAdapter);

        recyclerViewRevenue.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initViews() {
        Log.d(TAG, "initViews method");

        recyclerViewRevenue = binding.recyclerViewRevenue;

        FloatingActionButton bAddRevenue = binding.floatingActionButtonAdd;
        bAddRevenue.setOnClickListener(view -> {
            Log.i(TAG, "floating button add revenue clicked");
            StartAddActivity();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume method");

        updateRevenueAdapter();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateRevenueAdapter() {
        Log.d(TAG, "updateRevenueAdapter method");

        revenues.clear();
        revenues.addAll(revenueDAO.getAll());
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void StartAddActivity() {
        Log.d(TAG, "StartAddActivity method");

        Intent intent = new Intent(getContext(), AddActivity.class);
        intent.putExtra(ADDING_OBJECT_TYPE, ADDING_OBJ_REVENUE_TYPE);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView method");

        super.onDestroyView();
        binding = null;
    }
}
