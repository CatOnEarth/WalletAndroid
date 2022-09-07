package com.snail.wallet.MainScreen.ui.revenue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.snail.wallet.databinding.FragmentRevenueBinding;

public class RevenueFragment extends Fragment {

    private FragmentRevenueBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RevenueViewModel revenueViewModel =
                new ViewModelProvider(this).get(RevenueViewModel.class);

        binding = FragmentRevenueBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}