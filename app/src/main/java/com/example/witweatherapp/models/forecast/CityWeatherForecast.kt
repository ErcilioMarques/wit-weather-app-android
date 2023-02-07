package com.example.witweatherapp.models.forecast

data class CityWeatherForecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<CityWeatherForecastSingle>,
    val message: Int
)