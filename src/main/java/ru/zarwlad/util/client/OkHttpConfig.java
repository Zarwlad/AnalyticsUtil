package ru.zarwlad.util.client;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

class OkHttpConfig {
    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(600_000, TimeUnit.MILLISECONDS)
            .build();
}
