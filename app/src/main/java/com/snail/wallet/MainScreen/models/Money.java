package com.snail.wallet.MainScreen.models;


import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Money {
    @PrimaryKey (autoGenerate=true)
    @ColumnInfo(name = "id")
    public int    id;
    @ColumnInfo(name = "value")
    public double value;
    @ColumnInfo(name = "currency")
    public byte   currency;
    @ColumnInfo(name = "description")
    public String description;

    public Money(double value, byte currency, String description) {
        this.value       = value;
        this.currency    = currency;
        this.description = description;
    }

    public Money(double value, byte currency) {
        this.value    = value;
        this.currency = currency;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public double getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public byte getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     */
    public void setCurrency(byte currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
