package com.snail.wallet.MainScreen.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.snail.wallet.MainScreen.models.MoneySource;
import com.snail.wallet.R;

import java.util.ArrayList;

public class SourceAdapter extends ArrayAdapter<MoneySource>{
    public SourceAdapter(Context context, ArrayList<MoneySource> sources) {
        super(context, 0, sources);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_source_spinner, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.textViewMoneySourceSpinnerRevenueAdapter);
        MoneySource currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getSource());
        }
        return convertView;
    }
}
