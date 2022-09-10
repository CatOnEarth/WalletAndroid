package com.snail.wallet.MainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.snail.wallet.LoginScreen.LoginActivity;
import com.snail.wallet.MainScreen.SharedPrefManager.PermanentStorage;
import com.snail.wallet.MainScreen.activities.AddActivity;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.CategoryDAO.CategoryDAO;
import com.snail.wallet.MainScreen.db.CurrencyDAO.CurrencyDAO;
import com.snail.wallet.MainScreen.db.StorageLocationDAO.StorageLocationDAO;
import com.snail.wallet.MainScreen.models.parametrs.Category;
import com.snail.wallet.MainScreen.models.parametrs.Currency;
import com.snail.wallet.MainScreen.models.parametrs.StorageLocation;
import com.snail.wallet.R;
import com.snail.wallet.databinding.ActivityWalletBinding;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class WalletActivity extends AppCompatActivity {
    private AppBarConfiguration   mAppBarConfiguration;

    public static final int TYPE_CATEGORY         = 1;
    public static final int TYPE_CURRENCY         = 2;
    public static final int TYPE_STORAGE_LOCATION = 3;

    public static final String APP_PREFERENCES_IS_INIT_DB   = "is_init_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PermanentStorage.init(this);
        if (!PermanentStorage.getPropertyBoolean(APP_PREFERENCES_IS_INIT_DB)) {
            InitDB();
            PermanentStorage.addPropertyBoolean(APP_PREFERENCES_IS_INIT_DB, true);
        }

        super.onCreate(savedInstanceState);

        com.snail.wallet.databinding.ActivityWalletBinding binding = ActivityWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarWallet.toolbar);

        DrawerLayout drawer           = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_wallet);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View mHeaderView =  navigationView.getHeaderView(0);

        TextView textViewUsername = mHeaderView.findViewById(R.id.textViewNavViewUsername);
        TextView textViewEmail    = mHeaderView.findViewById(R.id.textViewNavViewEmail);

        Intent intent = getIntent();
        textViewUsername.setText(intent.getStringExtra(LoginActivity.APP_PREFERENCES_USERNAME));
        textViewEmail.setText(   intent.getStringExtra(LoginActivity.APP_PREFERENCES_USER_EMAIL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wallet, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_wallet);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void InitDB() {
        AppDatabase db          = App.getInstance().getAppDatabase();

        initCategoryTable(db);
        initCurrencyTable(db);
        initStorageLocationTable(db);
    }

    private void initCategoryTable(AppDatabase db) {
        CategoryDAO categoryDAO = db.categoryDAO();
        categoryDAO.insert(new Category( AddActivity.ADDING_REVENUE, "Зарплата"));
        categoryDAO.insert(new Category(AddActivity.ADDING_REVENUE, "Акции"));
        categoryDAO.insert(new Category(AddActivity.ADDING_REVENUE, "Квартира"));

        categoryDAO.insert(new Category(AddActivity.ADDING_EXPENSES, "Кварплата"));
        categoryDAO.insert(new Category(AddActivity.ADDING_EXPENSES, "Продукты"));
        categoryDAO.insert(new Category(AddActivity.ADDING_EXPENSES, "Занятия"));
    }

    private void initCurrencyTable(AppDatabase db) {
        CurrencyDAO currencyDAO = db.currencyDAO();
        currencyDAO.insert(new Currency("Российский рубль", "₽"));
        currencyDAO.insert(new Currency("Доллар США", "$"));
        currencyDAO.insert(new Currency("Евро", "€"));
    }

    private void initStorageLocationTable(AppDatabase db) {
        StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
        storageLocationDAO.insert(new StorageLocation( "Банк"));
        storageLocationDAO.insert(new StorageLocation( "Кошелек"));
        storageLocationDAO.insert(new StorageLocation( "Сейф"));
    }
}