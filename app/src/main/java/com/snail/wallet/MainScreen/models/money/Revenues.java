package com.snail.wallet.MainScreen.models.money;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "revenues")
public class Revenues extends Money {
    @ColumnInfo(name = "storage_location")
    public long storage_location;

    public Revenues(long id, double value, int type_currency, long id_category, int date_day, int date_month, int date_year, String description) {
        super(id, value, type_currency, id_category, date_day, date_month, date_year, description);
    }

    @Ignore
    public Revenues(double value, int type_currency, long id_category, int date_day, int date_month, int date_year, String description, long storage_location) {
        super(value, type_currency, id_category, date_day, date_month, date_year, description);
        this.storage_location = storage_location;
    }

    public long getStorage_location() {
        return storage_location;
    }
}
