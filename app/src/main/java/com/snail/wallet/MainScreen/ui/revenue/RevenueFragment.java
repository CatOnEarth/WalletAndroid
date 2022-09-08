package com.snail.wallet.MainScreen.ui.revenue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.snail.wallet.MainScreen.activities.RevenueAddActivity;
import com.snail.wallet.databinding.FragmentRevenueBinding;

public class RevenueFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    private FragmentRevenueBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RevenueViewModel revenueViewModel =
                new ViewModelProvider(this).get(RevenueViewModel.class);

        binding = FragmentRevenueBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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