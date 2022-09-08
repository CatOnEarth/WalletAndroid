package com.snail.wallet.MainScreen.SharedPrefManager;

import android.content.Context;
import android.content.SharedPreferences;

public class PermanentStorage {
    public static final String STORAGE_NAME = "WalletPermanent";

    private static SharedPreferences settings = null;
    private static SharedPreferences.Editor editor = null;
    private static Context context = null;

    public static void init( Context cntxt ){
        context = cntxt;
    }

    private static void init(){
        settings = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        editor   = settings.edit();
    }

    public static void addPropertyString( String name, String value ){
        if( settings == null ){
            init();
        }
        editor.putString( name, value );
        editor.apply();
    }

    public static void addPropertyInt( String name, int value ){
        if( settings == null ){
            init();
        }
        editor.putInt( name, value );
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
        return settings.getString( name, null );
    }

    public static int getPropertyInt( String name ){
        if( settings == null ){
            init();
        }
        return settings.getInt( name, -1 );
    }

    public static Boolean getPropertyBoolean( String name ){
        if( settings == null ){
            init();
        }
        return settings.getBoolean( name, false );
    }
}
