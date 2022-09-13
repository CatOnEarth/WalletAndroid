package com.snail.wallet.MainScreen.models.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatesRetrofit {
    @SerializedName("USD")
    @Expose
    public double USD;

    @SerializedName("EUR")
    @Expose
    public double EUR;

    @SerializedName("TRY")
    @Expose
    public double TRY;

    public double getUSD() {
        return USD;
    }

    public double getEUR() {
        return EUR;
    }

    public double getTRY() {
        return TRY;
    }
}
