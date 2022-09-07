package com.snail.wallet.MainScreen.models;


import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Money {
    @PrimaryKey
    @ColumnInfo(name = "id")
    protected int    id;
    @ColumnInfo(name = "value")
    protected double value;
    @ColumnInfo(name = "currency")
    protected byte   currency;
    @ColumnInfo(name = "description")
    protected String description;

    public Money(int id, double value, byte currency, String description) {
        this.id = id;
        this.value = value;
        this.currency = currency;
        this.description = description;
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
