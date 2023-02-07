package com.example.witweatherapp.models

import java.util.SimpleTimeZone

data class CityWeather(
                       val clouds: Clouds,
                       val coord: Coord,
                       val dt: Int,
                       val id: Int,
                       val main: Main,
                       val name: String,
                       val sys: Sys,
                       val visibility: Int,
                       val weather: List<Weather>,
                       val wind: Wind,
                       val cod:Int?,
                       val timeZone: Int?
)
