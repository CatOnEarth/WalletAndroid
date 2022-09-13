package com.snail.wallet.MainScreen.models.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExchangeRate {
    @SerializedName("disclaimer")
    @Expose
    public String   disclaimer;

    @SerializedName("data")
    @Expose
    public String   data;

    @SerializedName("timestamp")
    @Expose
    public String   timestamp;

    @SerializedName("base")
    @Expose
    public String   base;

    @SerializedName("rates")
    @Expose
    public RatesRetrofit ratesRetrofit;

    public RatesRetrofit getRates() {
        return ratesRetrofit;
    }
}
