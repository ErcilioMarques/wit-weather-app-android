package com.example.witweatherapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.witweatherapp.R
import com.example.witweatherapp.models.CityWeather
import java.text.SimpleDateFormat
import java.util.*

class CitiesListViewAdapter(var mCtx:Context, var resources: Int, var items:List<CityWeather> ): ArrayAdapter<CityWeather>(mCtx,resources,items) {

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view:View = layoutInflater.inflate(resources, null)

        var cityName:TextView = view.findViewById(R.id.city_name_tv)
        var cityTime:TextView = view.findViewById(R.id.city_time_tv)
        var citWeather:TextView = view.findViewById(R.id.city_weather_tv)
        var citWeatherStatus:TextView = view.findViewById(R.id.city_weather_status_tv)
        var cityTempMax:TextView = view.findViewById(R.id.city_temp_max_tv)
        var cityTempMin:TextView = view.findViewById(R.id.city_temp_min_tv)

        var cityWeaterIcon: ImageView = view.findViewById(R.id.city_weather_icon)

        var mItem: CityWeather = items[position]
        cityWeaterIcon.setImageDrawable(WeatherIconsHelper.fromWMO(mItem.weather[0].icon)
            ?.let { mCtx.getDrawable(it.iconRes) })

       cityName.text = mItem.name
        cityTime.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(mItem.dt.toLong()*1000))
        citWeather.text = mItem.main.temp.toString()+ "ºC"
        citWeatherStatus.text = mItem.weather[0].main.capitalize()
        cityTempMax.text ="Max: " +  mItem.main.temp_max.toString()+ "ºC"
        cityTempMin.text = "Min: " + mItem.main.temp_min.toString()+ "ºC"


        return view
    }
}