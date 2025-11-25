package com.albatech.casadei.core.network

import android.util.Log
import com.albatech.casadei.api.auth.AuthApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

    private const val BASE_URL = "https://api.casadei.id/"

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("apilog", message)
    }.apply {
        // BODY = log URL, header, dan body request/response
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            // nanti bisa tambah auth interceptor di sini juga kalo udah ada token
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)    // ← ini yang nanti keliatan di log
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }
}
