package com.example.playground.data.remote.retrofit

import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

enum class RetrofitClient {
    INSTANCE;

    companion object {
        const val GITHUB_DATABASE_URL = "https://my-json-server.typicode.com/"
    }

    fun getRetrofitClient(): Single<Retrofit> {

        val httpClient = OkHttpClient.Builder()

        // Add default interceptors
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS
        logging.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addNetworkInterceptor(logging)

        val retrofit = Retrofit.Builder()
            .baseUrl(GITHUB_DATABASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()

        return Single.just(retrofit)
    }
}