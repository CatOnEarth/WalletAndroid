package com.snail.wallet.MainScreen;

import static com.snail.wallet.WalletConstants.ADDING_OBJ_EXPENSES_TYPE;
import static com.snail.wallet.WalletConstants.ADDING_OBJ_REVENUE_TYPE;
import static com.snail.wallet.WalletConstants.APP_PREFERENCES_IS_INIT_DB;
import static com.snail.wallet.WalletConstants.APP_PREFERENCES_USERNAME;
import static com.snail.wallet.WalletConstants.APP_PREFERENCES_USER_EMAIL;
import static com.snail.wallet.WalletConstants.CODE_ABOUT_DIALOG;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CURRENCY_DOLLAR;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CURRENCY_EURO;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CURRENCY_RUBLE;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CURRENCY_TURKISH_LIRA;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.snail.wallet.MainScreen.SharedPrefManager.PermanentStorage;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.CategoryDAO.CategoryDAO;
import com.snail.wallet.MainScreen.db.CurrencyDAO.CurrencyDAO;
import com.snail.wallet.MainScreen.db.StorageLocationDAO.StorageLocationDAO;
import com.snail.wallet.MainScreen.models.parametrs.Category;
import com.snail.wallet.MainScreen.models.parametrs.Currency;
import com.snail.wallet.MainScreen.models.parametrs.StorageLocation;
import com.snail.wallet.MainScreen.ui.dialogs.InfoDialogFragment;
import com.snail.wallet.R;
import com.snail.wallet.databinding.ActivityWalletBinding;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class WalletActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private AppBarConfiguration   mAppBarConfiguration;

    private TextView textViewUsername;
    private TextView textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate method");

        CheckIsInitDB();

        super.onCreate(savedInstanceState);

        initViews();
        initUserData();
    }

    private void CheckIsInitDB() {
        Log.d(TAG, "CheckIsInitDB method");

        PermanentStorage.init(this);
        if (!PermanentStorage.getPropertyBoolean(APP_PREFERENCES_IS_INIT_DB)) {
            Log.d(TAG, "DB is not init");
            InitDB();
            PermanentStorage.addPropertyBoolean(APP_PREFERENCES_IS_INIT_DB, true);
        }

        Log.d(TAG, "DB is init");
    }

    private void initViews() {
        Log.d(TAG, "initViews method");

        com.snail.wallet.databinding.ActivityWalletBinding binding = ActivityWalletBinding
                                                                      .inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarWallet.toolbar);

        DrawerLayout drawer           = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_calc_currency,
                R.id.nav_settings, R.id.nav_userdata)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this,
                                                        R.id.nav_host_fragment_content_wallet);
        NavigationUI.setupActionBarWithNavController(this, navController,
                                                                        mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View mHeaderView =  navigationView.getHeaderView(0);

        textViewUsername = mHeaderView.findViewById(R.id.textViewNavViewUsername);
        textViewEmail    = mHeaderView.findViewById(R.id.textViewNavViewEmail);
    }

    private void initUserData() {
        Log.d(TAG, "initUserData method");

        Intent intent = getIntent();
        textViewUsername.setText(intent.getStringExtra(APP_PREFERENCES_USERNAME));
        textViewEmail.setText(   intent.getStringExtra(APP_PREFERENCES_USER_EMAIL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu method");

        getMenuInflater().inflate(R.menu.wallet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected method");

        if (item.getItemId() == R.id.about_wallet_activity) {
            Log.d(TAG, "About choose in menu actionBar");
            startAboutDialog();
        } else {
            Log.w(TAG, "Choose some item in menu, but can't find this view elem");
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d(TAG, "onSupportNavigateUp method");

        NavController navController = Navigation.findNavController(this,
                                                            R.id.nav_host_fragment_content_wallet);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void InitDB() {
        Log.d(TAG, "InitDB method");

        AppDatabase db          = App.getInstance().getAppDatabase();

        initCategoryTable(db);
        initCurrencyTable(db);
        initStorageLocationTable(db);
    }

    private void initCategoryTable(AppDatabase db) {
        Log.d(TAG, "initCategoryTable method");

        CategoryDAO categoryDAO = db.categoryDAO();
        categoryDAO.insert(new Category(ADDING_OBJ_REVENUE_TYPE, "Зарплата"));
        categoryDAO.insert(new Category(ADDING_OBJ_REVENUE_TYPE, "Акции"));
        categoryDAO.insert(new Category(ADDING_OBJ_REVENUE_TYPE, "Квартира"));

        categoryDAO.insert(new Category(ADDING_OBJ_EXPENSES_TYPE, "Кварплата"));
        categoryDAO.insert(new Category(ADDING_OBJ_EXPENSES_TYPE, "Продукты"));
        categoryDAO.insert(new Category(ADDING_OBJ_EXPENSES_TYPE, "Занятия"));
    }

    private void initCurrencyTable(AppDatabase db) {
        Log.d(TAG, "initCurrencyTable method");

        CurrencyDAO currencyDAO = db.currencyDAO();
        currencyDAO.insert(new Currency(CODE_TYPE_CURRENCY_RUBLE, "Российский рубль",
                                                                                "₽"));
        currencyDAO.insert(new Currency(CODE_TYPE_CURRENCY_DOLLAR, "Доллар США",
                                                                                "$"));
        currencyDAO.insert(new Currency(CODE_TYPE_CURRENCY_EURO, "Евро",
                                                                                "€"));
        currencyDAO.insert(new Currency(CODE_TYPE_CURRENCY_TURKISH_LIRA, "Турецкая лира",
                                                                                "₺"));
    }

    private void initStorageLocationTable(AppDatabase db) {
        Log.d(TAG, "initStorageLocationTable method");

        StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
        storageLocationDAO.insert(new StorageLocation( "Банк"));
        storageLocationDAO.insert(new StorageLocation( "Кошелек"));
        storageLocationDAO.insert(new StorageLocation( "Сейф"));
    }

    private void startAboutDialog() {
        Log.d(TAG, "aboutDialog method");

        InfoDialogFragment dialog = new InfoDialogFragment(CODE_ABOUT_DIALOG, "О приложении",
                "Информация о приолжении", true, "Ок",
                false, "");

        dialog.show(getSupportFragmentManager(), TAG);
    }
}
