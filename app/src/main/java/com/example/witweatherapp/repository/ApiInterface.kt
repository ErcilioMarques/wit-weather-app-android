package com.example.witweatherapp.repository

import com.example.witweatherapp.BuildConfig
import com.example.witweatherapp.BuildConfig.APPLICATION_ID
import com.example.witweatherapp.BuildConfig.OPEN_WEATHER_API_ID
import com.example.witweatherapp.models.CitiesWeather
import com.example.witweatherapp.models.CityWeather
import com.example.witweatherapp.models.forecast.CityWeatherForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("group")
    fun getCitiesWeatherData(@Query("id") id: String,@Query("units") units: String="metric",@Query("appid") appid: String= OPEN_WEATHER_API_ID): Call<CitiesWeather>

    @GET("weather")
    fun getMyCityWeatherData(@Query("lat") lat: String,@Query("lon") lon: String,@Query("units") units: String="metric",@Query("appid") appid: String= OPEN_WEATHER_API_ID): Call<CityWeather>

    @GET("forecast")
    fun getMyCityWeatherDataForecast(@Query("lat") lat: String,@Query("lon") lon: String,@Query("units") units: String="metric",@Query("appid") appid: String= OPEN_WEATHER_API_ID): Call<CityWeatherForecast>

}