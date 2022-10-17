package com.sonnv.kidsonline.service

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.util.concurrent.TimeUnit

object APIModule {

    private const val BASE_URL = "https://kid.dzungnguyen.vn/"
    private var retrofit: Retrofit ?= null

    fun getRetrofitClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(provideOkhttpClient())
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .build()
        }

        return retrofit
    }

    fun provideGson(): Gson? {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    fun provideOkhttpClient(): OkHttpClient? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val cookieHandler: CookieHandler = CookieManager()
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .cookieJar(JavaNetCookieJar(cookieHandler))
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun getAPIService(): APIService? {
        return getRetrofitClient()?.create(APIService::class.java)
    }
}