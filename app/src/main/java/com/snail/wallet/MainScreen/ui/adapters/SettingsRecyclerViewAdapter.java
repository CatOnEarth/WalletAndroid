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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.CategoryDAO.CategoryDAO;
import com.snail.wallet.MainScreen.db.StorageLocationDAO.StorageLocationDAO;
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
        String name = "";

        if (typeSetting == CODE_TYPE_CATEGORY_REVENUE || typeSetting == CODE_TYPE_CATEGORY_EXPENSES) {
            name = ((Category)localData.get(position)).getName();
            viewHolder.textViewElemSettingName.setText(name);
        } else if (typeSetting == CODE_TYPE_PARAM_STORAGE_LOCATION) {
            name = ((StorageLocation)(localData.get(position))).getLocation();
            viewHolder.textViewElemSettingName.setText(name);
        }

        initClickListenerDelete(viewHolder, position);
        initClickListenerEdit(viewHolder, name, position);
    }

    private void initClickListenerEdit(ViewHolder viewHolder, String old_name, int position) {
        Log.d(TAG, "initClickListenerEdit method");

        viewHolder.imageButtonEditElemSetting.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Переименовать элемент")
                    .setMessage("Введите новое имя(макс. 32 символа)");

            final EditText input = new EditText(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setText(old_name);
            int maxLength        = 32;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0]            = new InputFilter.LengthFilter(maxLength);
            input.setFilters(fArray);
            input.setLayoutParams(lp);
            builder.setView(input);

            builder
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> editNameElem(input.getText().toString(), old_name,
                            viewHolder.getBindingAdapterPosition()))
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        });
    }

    private void editNameElem(String new_name, String old_name, int pos) {
        Log.d(TAG, "editNameElem method");

        if (old_name.equals(new_name)) {
            return;
        }

        AppDatabase db = App.getInstance().getAppDatabase();

        if (new_name.length() == 0) return;

        if (typeSetting == CODE_TYPE_CATEGORY_REVENUE || typeSetting == CODE_TYPE_CATEGORY_EXPENSES) {
            if (!isExistNewNameCategory(db, ((Category)localData.get(pos)).getType(), new_name)) {
                ((Category)localData.get(pos)).setName(new_name);
                db.categoryDAO().update(((Category)localData.get(pos)));
                notifyItemChanged(pos);

                return;
            }
        } else if (typeSetting == CODE_TYPE_PARAM_STORAGE_LOCATION) {
            if (!isExistNewNameStorageLocation(db, new_name)) {
                ((StorageLocation)(localData.get(pos))).setLocation(new_name);
                db.storageLocationDAO().update(((StorageLocation)(localData.get(pos))));
                notifyItemChanged(pos);

                return;
            }
        } else {
            Log.w(TAG, "typeSetting unknown");
            return;
        }

        Toast.makeText(context, "Такое имя уже существует", Toast.LENGTH_LONG).show();
    }

    private boolean isExistNewNameCategory(AppDatabase db, int type_category, String new_name) {
        Log.d(TAG, "isExistNewNameCategory method");

        CategoryDAO categoryDAO = db.categoryDAO();
        return categoryDAO.getByNameAndTypeCategory(type_category, new_name).size() != 0;
    }

    private boolean isExistNewNameStorageLocation(AppDatabase db, String new_name) {
        Log.d(TAG, "isExistNewNameStorageLocation method");

        StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
        return storageLocationDAO.getLocationByName(new_name).size() != 0;
    }

    private void initClickListenerDelete(ViewHolder viewHolder, int position) {
        Log.d(TAG, "initClickListenerDelete method");

        viewHolder.imageButtonDeleteElemSetting.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Удалить элемент");
            builder.setPositiveButton(android.R.string.yes, (dialog, which) -> deleteElem(viewHolder.getBindingAdapterPosition()));
            builder.setNegativeButton(android.R.string.no, null);

            if (typeSetting == CODE_TYPE_PARAM_STORAGE_LOCATION) {
                builder.setMessage("Будут удалены все элеметны, в которых указано данное место хранения");
            } else if (typeSetting == CODE_TYPE_CATEGORY_REVENUE || typeSetting == CODE_TYPE_CATEGORY_EXPENSES) {
                builder.setMessage("Будут удалены все элеметны с этой категорией");
            } else {
                Log.w(TAG, "typeSetting unknown");
            }

            builder.show();
        });


    }

    private void deleteElem(int pos) {
        Log.d(TAG, "deleteElem method");

        AppDatabase db = App.getInstance().getAppDatabase();
        if (typeSetting == CODE_TYPE_CATEGORY_REVENUE || typeSetting == CODE_TYPE_CATEGORY_EXPENSES) {
            db.categoryDAO().delete(((Category)localData.get(pos)));
            if (typeSetting == CODE_TYPE_CATEGORY_REVENUE) {
                db.revenueDAO().deleteByIdCategory(((Category)localData.get(pos)).getId());
            } else {
                db.expensesDAO().deleteByIdCategory(((Category)localData.get(pos)).getId());
            }
        } else if (typeSetting == CODE_TYPE_PARAM_STORAGE_LOCATION) {
            db.storageLocationDAO().delete(((StorageLocation)(localData.get(pos))));
            db.revenueDAO().deleteByStorageLocation(((StorageLocation)(localData.get(pos))).getId());
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
