package com.snail.wallet.MainScreen.SharedPrefManager;

import static com.snail.wallet.WalletConstants.SHARED_PREF_MANAGER_STORAGE_NAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PermanentStorage {
    private static SharedPreferences settings      = null;
    private static SharedPreferences.Editor editor = null;
    @SuppressLint("StaticFieldLeak")
    private static Context context                 = null;

    public static void init( Context cxt ){
        context = cxt;
    }

    public static void clearPermanentStorage() {
        context.getSharedPreferences(SHARED_PREF_MANAGER_STORAGE_NAME, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }

    private static void init(){
        settings = context.getSharedPreferences(SHARED_PREF_MANAGER_STORAGE_NAME, Context.MODE_PRIVATE);
        editor   = settings.edit();
    }

    public static void addPropertyString( String name, String value ){
        if( settings == null ){
            init();
        }

        editor.putString( name, value );
        editor.apply();
    }

    public static void addPropertyBoolean( String name, boolean value ){
        if( settings == null ){
            init();
        }

        editor.putBoolean( name, value );
        editor.apply();
    }

    public static String getPropertyString( String name ){
        if( settings == null ){
            init();
        }

        return settings.getString( name, "" );
    }

    public static Boolean getPropertyBoolean( String name ){
        if( settings == null ){
            init();
        }

        return settings.getBoolean( name, false );
    }
}
