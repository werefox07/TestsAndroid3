package com.geekbrains.lesson1.clear.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInit {

    private static final String BASE_URL = "https://newsapi.org/";

    private static Api api;

    public static synchronized Api newApiInstance() {
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Api.class);
        }
        return api;
    }
}
