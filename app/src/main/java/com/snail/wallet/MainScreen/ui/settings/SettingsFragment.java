package com.snail.wallet.MainScreen.ui.settings;

import static com.snail.wallet.WalletConstants.CODE_TYPE_CATEGORY_EXPENSES;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CATEGORY_REVENUE;
import static com.snail.wallet.WalletConstants.CODE_TYPE_STORAGE_LOCATION;
import static com.snail.wallet.WalletConstants.LIST_SETTINGS;
import static com.snail.wallet.WalletConstants.SETTING_TYPE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.snail.wallet.MainScreen.activities.EditSettingActivity;
import com.snail.wallet.R;
import com.snail.wallet.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    private FragmentSettingsBinding binding;

    private ListView listViewSettings;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initFindViews(root);

        ArrayAdapter<String> adapterListSettings = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, LIST_SETTINGS);

        listViewSettings.setAdapter(adapterListSettings);
        listViewSettings.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ++position;
                if        (position == CODE_TYPE_CATEGORY_REVENUE) {
                    startEditingSettingActivity(CODE_TYPE_CATEGORY_REVENUE);
                } else if (position == CODE_TYPE_CATEGORY_EXPENSES) {
                    startEditingSettingActivity(CODE_TYPE_CATEGORY_EXPENSES);
                } else if (position == CODE_TYPE_STORAGE_LOCATION) {
                    startEditingSettingActivity(CODE_TYPE_STORAGE_LOCATION);
                }
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initFindViews(View root) {
        listViewSettings = root.findViewById(R.id.listViewSettingFragment);
    }

    private void startEditingSettingActivity(int code_type) {
        Intent intent = new Intent(requireContext(), EditSettingActivity.class);
        intent.putExtra(SETTING_TYPE, code_type);
        startActivity(intent);
    }
}