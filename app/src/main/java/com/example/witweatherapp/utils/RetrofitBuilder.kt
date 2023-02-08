package com.example.witweatherapp.utils

import com.example.witweatherapp.BuildConfig
import com.example.witweatherapp.repository.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    companion object{
        private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        var retrofitBuilder: Retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_API_URL)
            .build()


        fun weatherApi ():ApiInterface{
            return retrofitBuilder.create(ApiInterface::class.java)
        }
    }
}