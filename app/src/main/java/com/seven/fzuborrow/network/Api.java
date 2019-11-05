package com.seven.fzuborrow.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class Api {

    private Api(){}

    private static ApiInterface api;

    public static ApiInterface get() {
        if (api == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(System.out::println);
            logging.level(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();
            api = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("http://49.235.150.59:8080/jiebei/")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(ApiInterface.class);
        }
        return api;
    }
}
