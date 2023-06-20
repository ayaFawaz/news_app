package com.example.dailynews.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiService {

    private const val baseUrl = "https://newsapi.org/"
    private const val apiKey = "97df9431f0954d169a90df48f0f98c71"

    private val logging = HttpLoggingInterceptor().apply {
         HttpLoggingInterceptor.Level.BODY }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(40, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .writeTimeout(40, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val url = chain.request().url.newBuilder()
                .addQueryParameter("apiKey", apiKey)
                .addQueryParameter("country", "us")
                .build()
            val requestBuilder = chain.request().newBuilder()
                .url(url)
                .build()
            chain.proceed(requestBuilder)
        }
        .build()


    private fun getInstance() : Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getNewsApi() : NewsApi {
       return getInstance().create(NewsApi::class.java)
    }
}
