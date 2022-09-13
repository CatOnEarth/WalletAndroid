package com.snail.wallet.MainScreen.retrofit;

import com.snail.wallet.MainScreen.models.retrofit.ExchangeRate;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ExchangeRateService {
    @GET("/latest.js")
    Call<ExchangeRate> getExchangeRate();
}
