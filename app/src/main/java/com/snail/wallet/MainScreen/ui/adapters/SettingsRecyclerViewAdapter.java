package com.snail.wallet.MainScreen.ui.adapters;

import static com.snail.wallet.WalletConstants.CODE_TYPE_CATEGORY_EXPENSES;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CATEGORY_REVENUE;
import static com.snail.wallet.WalletConstants.CODE_TYPE_PARAM_STORAGE_LOCATION;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.models.parametrs.Category;
import com.snail.wallet.MainScreen.models.parametrs.StorageLocation;
import com.snail.wallet.MainScreen.ui.dialogs.InfoDialogButtonListener;
import com.snail.wallet.MainScreen.ui.dialogs.InfoDialogFragment;
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
        if (typeSetting == CODE_TYPE_CATEGORY_REVENUE || typeSetting == CODE_TYPE_CATEGORY_EXPENSES) {
            viewHolder.textViewElemSettingName.setText(((Category)localData.get(position)).getName());
        } else if (typeSetting == CODE_TYPE_PARAM_STORAGE_LOCATION) {
            viewHolder.textViewElemSettingName.setText(((StorageLocation)(localData.get(position)))
                                                                            .getLocation());
        }

        viewHolder.imageButtonDeleteElemSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int pos = viewHolder.getAdapterPosition();
                                AppDatabase db = App.getInstance().getAppDatabase();
                                if (typeSetting == CODE_TYPE_CATEGORY_REVENUE || typeSetting == CODE_TYPE_CATEGORY_EXPENSES) {
                                    db.categoryDAO().delete(((Category)localData.get(pos)));
                                } else if (typeSetting == CODE_TYPE_PARAM_STORAGE_LOCATION) {
                                    db.storageLocationDAO().delete(((StorageLocation)(localData.get(pos))));
                                }

                                localData.remove(pos);
                                notifyItemRemoved(pos);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return localData.size();
    }


}
