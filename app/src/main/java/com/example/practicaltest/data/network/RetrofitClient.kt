package com.example.practicaltest.network

import com.example.practicaltest.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors


object RetrofitClient {

    private var retrofit: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    val client: Retrofit
        get() {
            if (retrofit == null) {
                synchronized(Retrofit::class.java) {
                    if (retrofit == null) {


                        // for pretty log of HTTP request-response
                        val logging = HttpLoggingInterceptor()
                        logging.level = HttpLoggingInterceptor.Level.BODY
                        val client = OkHttpClient.Builder()
                            .addInterceptor(logging)
                            .addInterceptor(QueryParameterAddInterceptor())
                            .build()

                        retrofit = Retrofit.Builder()
                                .baseUrl(BuildConfig.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .client(client)
                                .build()
                    }
                }

            }
            return retrofit!!
        }
}