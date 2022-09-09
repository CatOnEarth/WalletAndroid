package com.snail.wallet.MainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.snail.wallet.LoginScreen.LoginActivity;
import com.snail.wallet.MainScreen.SharedPrefManager.PermanentStorage;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.CategoryDAO.CategoryDAO;
import com.snail.wallet.MainScreen.db.CurrencyDAO.CurrencyDAO;
import com.snail.wallet.MainScreen.db.MoneySouceDAO.MoneySourceDAO;
import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;
import com.snail.wallet.MainScreen.db.StorageLocationDAO.StorageLocationDAO;
import com.snail.wallet.MainScreen.models.Category;
import com.snail.wallet.MainScreen.models.Coin;
import com.snail.wallet.MainScreen.models.Currency;
import com.snail.wallet.MainScreen.models.MoneySource;
import com.snail.wallet.MainScreen.models.StorageLocation;
import com.snail.wallet.R;
import com.snail.wallet.databinding.ActivityWalletBinding;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class WalletActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityWalletBinding binding;

    public static final String APP_PREFERENCES_IS_INIT_DB   = "is_init_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PermanentStorage.init(this);
        if (!PermanentStorage.getPropertyBoolean(APP_PREFERENCES_IS_INIT_DB)) {
            InitDB();
            PermanentStorage.addPropertyBoolean(APP_PREFERENCES_IS_INIT_DB, true);
        }

        super.onCreate(savedInstanceState);

        binding = ActivityWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarWallet.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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
        textViewEmail.setText(intent.getStringExtra(LoginActivity.APP_PREFERENCES_USER_EMAIL));
    }

    private void InitDB() {
        AppDatabase db          = App.getInstance().getAppDatabase();

        CategoryDAO categoryDAO = db.categoryDAO();

        categoryDAO.insert(new Category( 1, "Зарплата"));
         categoryDAO.insert(new Category(1, "Акции"));
        categoryDAO.insert(new Category(1, "Квартира"));

        CurrencyDAO currencyDAO = db.currencyDAO();

        currencyDAO.insert(new Currency("Российский рубль", "₽"));
        currencyDAO.insert(new Currency("Доллар США", "$"));
        currencyDAO.insert(new Currency("Евро", "€"));

        MoneySourceDAO moneySourceDAO = db.moneySourceDAO();

        moneySourceDAO.insert(new MoneySource( "Работа 1"));
        moneySourceDAO.insert(new MoneySource( "Работа 2"));
        moneySourceDAO.insert(new MoneySource( "Работа 3"));

        StorageLocationDAO storageLocationDAO = db.storageLocationDAO();

        storageLocationDAO.insert(new StorageLocation( "Банк"));
        storageLocationDAO.insert(new StorageLocation( "Кошелек"));
        storageLocationDAO.insert(new StorageLocation( "Сейф"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wallet, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_wallet);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}