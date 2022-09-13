package com.snail.wallet;

public class WalletConstants {
    public static final String SHARED_PREF_MANAGER_STORAGE_NAME = "WalletPermanent";
    public static final String APP_PREFERENCES_USERNAME         = "user_name";
    public static final String APP_PREFERENCES_USER_EMAIL       = "user_email";
    public static final String APP_PREFERENCES_IS_USER_LOG      = "is_user_log";
    public static final String APP_PREFERENCES_IS_INIT_DB       = "is_init_db";
    public static final String APP_PREFERENCES_IS_INIT_RATES    = "is_init_rates";

    public static final int CODE_TYPE_CURRENCY_RUBLE        = 0;
    public static final int CODE_TYPE_CURRENCY_DOLLAR       = 1;
    public static final int CODE_TYPE_CURRENCY_EURO         = 2;
    public static final int CODE_TYPE_CURRENCY_TURKISH_LIRA = 3;

    public static final int CODE_TYPE_PARAM_CATEGORY         = 1;
    public static final int CODE_TYPE_PARAM_CURRENCY         = 2;
    public static final int CODE_TYPE_PARAM_STORAGE_LOCATION = 3;

    public static final String ID_EDITING_OBJ               = "id_editing";
    public static final String VALUE_EDITING_OBJ            = "val_editing";
    public static final String DESC_EDITING_OBJ             = "desc_editing";
    public static final String CATEGORY_EDITING_OBJ         = "category_editing";
    public static final String CURRENCY_EDITING_OBJ         = "currency_editing";
    public static final String STORAGE_LOCATION_EDITING_OBJ = "storage_location_editing";

    public static final String ADDING_OBJECT_TYPE       = "obj_add";
    public static final int    ADDING_OBJ_REVENUE_TYPE  = 1;
    public static final int    ADDING_OBJ_EXPENSES_TYPE = 2;

    public static final String ID_SHOW_OBJ = "id_item";

    public static final String   SETTING_TYPE  = "setting_type";
    public static final String[] LIST_SETTINGS = {"Категории доходов", "Категории расходов",
                                                 "Места хранения"};
    public static final int CODE_TYPE_CATEGORY_REVENUE  = 1;
    public static final int CODE_TYPE_CATEGORY_EXPENSES = 2;
    public static final int CODE_TYPE_STORAGE_LOCATION  = 3;

    public static final int CODE_ABOUT_DIALOG = 50;
}
