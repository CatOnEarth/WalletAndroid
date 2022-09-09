package com.snail.wallet.MainScreen.ui.revenue;

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
import com.snail.wallet.MainScreen.activities.RevenueAddActivity;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;
import com.snail.wallet.MainScreen.models.Revenues;
import com.snail.wallet.MainScreen.ui.adapters.RevenueAdapter;
import com.snail.wallet.databinding.FragmentRevenueBinding;

import java.util.ArrayList;
import java.util.List;

public class RevenueFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    private FragmentRevenueBinding binding;

    private RecyclerView   recyclerView;
    private RevenueAdapter revenueAdapter;

    private RevenueDAO revenueDAO;
    private AppDatabase db;
    private List<Revenues> revenues;

    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRevenueBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerViewRevenue;

        db          = App.getInstance().getAppDatabase();
        revenueDAO = db.revenueDAO();

        revenues = new ArrayList<Revenues>();
        revenues.addAll(revenueDAO.getAll());
        revenueAdapter = new RevenueAdapter(revenues, getContext());

        recyclerView.setAdapter(revenueAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton bAddRevenue = binding.floatingActionButtonAdd;
        bAddRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "floating button clicked");
                StartRevenueAddActivity();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume Fragment");
        revenues.clear();
        revenues.addAll(revenueDAO.getAll());
        revenueAdapter.notifyItemRangeChanged(0, revenues.size());
    }

    private void StartRevenueAddActivity() {
        Intent intent = new Intent(getContext(), RevenueAddActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}