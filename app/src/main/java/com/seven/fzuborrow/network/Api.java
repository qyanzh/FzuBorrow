package com.seven.fzuborrow.network;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class Api {

    private Api() {
    }

    private static ApiInterface api;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor(x -> {
        System.out.println(x);
        Log.d("okhttp", x);
    }).setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(logging)
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .baseUrl("http://49.235.150.59:8080/jiebei/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public static ApiInterface get() {
        if (api == null) {
            api = retrofit.create(ApiInterface.class);
        }
        return api;
    }
}
