package com.snail.wallet.MainScreen.models.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatesRetrofit {
    @SerializedName("USD")
    @Expose
    double USD;

    @SerializedName("EUR")
    @Expose
    double EUR;

    @SerializedName("TRY")
    @Expose
    double TRY;

    public double getUSD() {
        return USD;
    }

    public void setUSD(double USD) {
        this.USD = USD;
    }

    public double getEUR() {
        return EUR;
    }

    public void setEUR(double EUR) {
        this.EUR = EUR;
    }

    public double getTRY() {
        return TRY;
    }

    public void setTRY(double TRY) {
        this.TRY = TRY;
    }
}
