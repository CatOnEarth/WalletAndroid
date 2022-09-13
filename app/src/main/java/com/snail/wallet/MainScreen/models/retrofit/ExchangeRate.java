package com.snail.wallet.MainScreen.models.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExchangeRate {
    @SerializedName("disclaimer")
    @Expose
    String   disclaimer;

    @SerializedName("data")
    @Expose
    String   data;

    @SerializedName("timestamp")
    @Expose
    String   timestamp;

    @SerializedName("base")
    @Expose
    String   base;

    @SerializedName("rates")
    @Expose
    RatesRetrofit ratesRetrofit;

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public RatesRetrofit getRates() {
        return ratesRetrofit;
    }

    public void setRates(RatesRetrofit ratesRetrofit) {
        this.ratesRetrofit = ratesRetrofit;
    }
}
