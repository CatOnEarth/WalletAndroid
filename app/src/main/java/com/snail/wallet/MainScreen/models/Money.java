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
    public int   currency;
    @ColumnInfo(name = "category")
    public int   category;
    @ColumnInfo(name = "description")
    public String description;

    public Money(int id, double value, int currency, int category, String description) {
        this.id          = id;
        this.value       = value;
        this.currency    = currency;
        this.category    = category;
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
    public int getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     */
    public void setCurrency(int currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     */
    public int getCategory() {
        return category;
    }

    /**
     *
     * @param category
     */
    public void setCategory(int category) {
        this.category = category;
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

    public String getCurrencyType() {
        switch (this.currency) {
            case 0:
                return "руб";
            case 1:
                return "$";
            case 2:
                return "евро";
        }

        return "валюта";
    }
}
