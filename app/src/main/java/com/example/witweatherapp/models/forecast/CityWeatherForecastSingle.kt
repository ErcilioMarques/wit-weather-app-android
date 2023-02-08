package com.example.witweatherapp.models.forecast

import com.example.witweatherapp.models.Clouds
import com.example.witweatherapp.models.Weather

data class CityWeatherForecastSingle(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: MainForecast,
    val pop: Double,
    val sys: SysForecast,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: WindForecast
)