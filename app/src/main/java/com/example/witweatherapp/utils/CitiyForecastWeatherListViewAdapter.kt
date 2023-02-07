package com.example.witweatherapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.witweatherapp.R
import com.example.witweatherapp.models.CityWeather
import com.example.witweatherapp.models.forecast.CityWeatherForecast
import com.example.witweatherapp.models.forecast.CityWeatherForecastSingle
import java.text.SimpleDateFormat
import java.util.*

class CitiyForecastWeatherListViewAdapter(var mCtx:Context, var resources: Int, var items:List<CityWeatherForecastSingle> ): RecyclerView.Adapter<CitiyForecastWeatherListViewAdapter.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_weather_forecast_list_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var mItem: CityWeatherForecastSingle = items[position]

        holder.cityWeaterIcon.setImageDrawable(WeatherIconsHelper.fromWMO(mItem.weather[0].icon)
            ?.let { mCtx.getDrawable(it.iconRes) })

        holder.cityForecastTime.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(mItem.dt.toLong()*1000))
        holder.citWeather.text = mItem.main.temp.toString()+ "ºC"


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var cityForecastTime:TextView = ItemView.findViewById(R.id.city_forecast_time_tv)
        var citWeather:TextView = ItemView.findViewById(R.id.city_forecast_weather_tv)
        var cityWeaterIcon: ImageView = ItemView.findViewById(R.id.city_forecast_weather_icon)

    }
}

