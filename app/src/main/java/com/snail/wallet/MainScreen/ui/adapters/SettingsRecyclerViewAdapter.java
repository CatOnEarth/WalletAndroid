package com.snail.wallet.MainScreen.ui.adapters;

import static com.snail.wallet.WalletConstants.CODE_TYPE_CATEGORY_EXPENSES;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CATEGORY_REVENUE;
import static com.snail.wallet.WalletConstants.CODE_TYPE_PARAM_STORAGE_LOCATION;

import android.content.Context;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.models.parametrs.Category;
import com.snail.wallet.MainScreen.models.parametrs.StorageLocation;
import com.snail.wallet.R;

import java.util.List;

public class SettingsRecyclerViewAdapter  extends RecyclerView.Adapter<SettingsRecyclerViewAdapter.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();

    private final int     typeSetting;
    private final List    localData;
    private final Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView    textViewElemSettingName;
        private final ImageButton imageButtonEditElemSetting;
        private final ImageButton imageButtonDeleteElemSetting;

        public ViewHolder(View view) {
            super(view);

            textViewElemSettingName      = view.findViewById(R.id.textViewRecyclerViewSettingElem);
            imageButtonEditElemSetting   = view.findViewById(R.id.imageButtonEditSettingElem);
            imageButtonDeleteElemSetting = view.findViewById(R.id.imageButtonDeleteSettingElem);
        }
    }

    public SettingsRecyclerViewAdapter(int typeSetting, List data, Context context) {
        Log.d(TAG, "SettingsRecyclerViewAdapter method");

        this.typeSetting = typeSetting;
        this.localData   = data;
        this.context     = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_setting_recyclerview, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder method");

        if (typeSetting == CODE_TYPE_CATEGORY_REVENUE || typeSetting == CODE_TYPE_CATEGORY_EXPENSES) {
            viewHolder.textViewElemSettingName.setText(((Category)localData.get(position)).getName());
        } else if (typeSetting == CODE_TYPE_PARAM_STORAGE_LOCATION) {
            viewHolder.textViewElemSettingName.setText(((StorageLocation)(localData.get(position)))
                                                                            .getLocation());
        }

        initClickListenerDelete(viewHolder, position);
        initClickListenerEdit(viewHolder, position);
    }

    private void initClickListenerEdit(ViewHolder viewHolder, int position) {
        Log.d(TAG, "initClickListenerEdit method");

        viewHolder.imageButtonEditElemSetting.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Переименовать элемент")
                    .setMessage("Введите новое имя(макс. 32 символа)");

            final EditText input = new EditText(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            int maxLength        = 32;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0]            = new InputFilter.LengthFilter(maxLength);
            input.setFilters(fArray);
            input.setLayoutParams(lp);
            builder.setView(input);

            builder
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> editNameElem(input.getText().toString(),
                            viewHolder.getAdapterPosition()))
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        });
    }

    private void editNameElem(String new_name, int pos) {
        Log.d(TAG, "editNameElem method");

        if (new_name.length() == 0) return;

        AppDatabase db = App.getInstance().getAppDatabase();

        if (typeSetting == CODE_TYPE_CATEGORY_REVENUE || typeSetting == CODE_TYPE_CATEGORY_EXPENSES) {
            ((Category)localData.get(pos)).setName(new_name);
            db.categoryDAO().update(((Category)localData.get(pos)));
        } else if (typeSetting == CODE_TYPE_PARAM_STORAGE_LOCATION) {
            ((StorageLocation)(localData.get(pos))).setLocation(new_name);
            db.storageLocationDAO().update(((StorageLocation)(localData.get(pos))));
        }

        notifyItemChanged(pos);
    }

    private void initClickListenerDelete(ViewHolder viewHolder, int position) {
        Log.d(TAG, "initClickListenerDelete method");

        viewHolder.imageButtonDeleteElemSetting.setOnClickListener(view -> new AlertDialog.Builder(context)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteElem(viewHolder.getAdapterPosition()))
                .setNegativeButton(android.R.string.no, null)
                .show());
    }

    private void deleteElem(int pos) {
        Log.d(TAG, "deleteElem method");

        AppDatabase db = App.getInstance().getAppDatabase();
        if (typeSetting == CODE_TYPE_CATEGORY_REVENUE || typeSetting == CODE_TYPE_CATEGORY_EXPENSES) {
            db.categoryDAO().delete(((Category)localData.get(pos)));
        } else if (typeSetting == CODE_TYPE_PARAM_STORAGE_LOCATION) {
            db.storageLocationDAO().delete(((StorageLocation)(localData.get(pos))));
        }

        localData.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount method");

        return localData.size();
    }
}
