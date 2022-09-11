package com.snail.wallet.MainScreen.ui.calcCurrency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.snail.wallet.databinding.FragmentCalcCurrencyBinding;

public class CalcCurrencyFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    private FragmentCalcCurrencyBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalcCurrencyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}