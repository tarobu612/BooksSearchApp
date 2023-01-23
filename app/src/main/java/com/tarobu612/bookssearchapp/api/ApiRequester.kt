package com.tarobu612.bookssearchapp.api

import com.tarobu612.bookssearchapp.GOOGLE_BOOKS_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiRequester {

    companion object {

        val retrofit: Retrofit.Builder by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build()

            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
        }

        inline fun <reified T> create(baseUrl: String): T {
            return retrofit
                .baseUrl(baseUrl)
                .build()
                .create(T::class.java)
        }

    }

}
