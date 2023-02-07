package com.example.witweatherapp.repository

import com.example.witweatherapp.models.CitiesWeather
import com.example.witweatherapp.models.CityWeather
import com.example.witweatherapp.models.forecast.CityWeatherForecast
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("group?id=2267057,3117735,2988507,2950159,2618425,3169070,2643743,2964574,3067696,2761369&units=metric&appid=df3f16bb7f7a39bfaaf78f6d0fd50685")
    fun getCitiesWeatherData(): Call<CitiesWeather>

    @GET("weather?lat=38.736946&lon=-9.142685&units=metric&appid=df3f16bb7f7a39bfaaf78f6d0fd50685")
    fun getMyCityWeatherData(): Call<CityWeather>

    @GET("forecast?lat=38.736946&lon=-9.142685&units=metric&appid=df3f16bb7f7a39bfaaf78f6d0fd50685")
    fun getMyCityWeatherDataForecast(): Call<CityWeatherForecast>

}