package com.example.witweatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.witweatherapp.models.CityWeather
import com.example.witweatherapp.models.forecast.CityWeatherForecast
import com.example.witweatherapp.repository.ApiInterface
import com.example.witweatherapp.utils.CitiyForecastWeatherListViewAdapter
import com.example.witweatherapp.utils.WeatherIconsHelper
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.android.synthetic.main.activity_city_weather_details.*
import kotlinx.android.synthetic.main.fragment_home_weather.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class CityWeatherDetailsActivity : AppCompatActivity() {

    lateinit private var weather_details_myCityWeather: CityWeather
    lateinit private var weather_details_myCityWeatherForecast: CityWeatherForecast
    lateinit private var cityWeatherForecastRc: RecyclerView
    lateinit private var  loader: ProgressBar
    lateinit private var mainContainer: LinearLayout
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit private var lat: String
    lateinit private var lon: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_weather_details)
        val intent = intent
        lat = intent.getStringExtra("lat").toString()
        lon = intent.getStringExtra("lon").toString()

        cityWeatherForecastRc = weather_details_city_forecast_weather_rc
        loader = weather_details_loader
        mainContainer = weather_details_mainContainer
        getMyCityWeatherData(this)
        getMyCityWeatherForecastData(this)

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun fillTheUI() {
        weather_details_city.text = weather_details_myCityWeather.sys.country
        weather_details_country.text = weather_details_myCityWeather.name

        weather_details_weather_icon.setImageDrawable(
            WeatherIconsHelper.fromWMO(weather_details_myCityWeather.weather[0].icon)
            ?.let { resources.getDrawable(it.iconRes) })
        weather_details_status.text = weather_details_myCityWeather.weather[0].main.capitalize()
        weather_details_temp.text =   weather_details_myCityWeather.main.temp.toString() + "ºC"
        weather_details_temp_min.text ="Min Temp: " +  weather_details_myCityWeather.main.temp_min.toString()+ "ºC"
        weather_details_temp_max.text ="Max Temp: " +  weather_details_myCityWeather.main.temp_max.toString()+ "ºC"
        weather_details_sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
            Date(weather_details_myCityWeather.sys.sunrise.toLong() * 1000)
        )
        weather_details_sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
            Date(weather_details_myCityWeather.sys.sunset.toLong() * 1000)
        )
        weather_details_wind.text = weather_details_myCityWeather.wind.speed.toString()
        weather_details_pressure.text = weather_details_myCityWeather.main.pressure.toString()
        weather_details_humidity.text = weather_details_myCityWeather.main.humidity.toString()
        weather_details_feels_like.text = weather_details_myCityWeather.main.feels_like.toString()+ "ºC"
    }

    private fun getMyCityWeatherData(context: Context) {
        val retorfitBuilder  = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_API_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retorfitBuilder.getMyCityWeatherData(lat, lon)

        retrofitData.enqueue(object : Callback<CityWeather?> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<CityWeather?>, response: Response<CityWeather?>) {
                Log.i("FecthingDataSucess", response.body().toString())
                val responseBody = response.body()!!
                weather_details_myCityWeather =responseBody

                fillTheUI()
            }

            override fun onFailure(call: Call<CityWeather?>, t: Throwable) {
                Log.i("FecthingDataFailure", "Failed")
            }
        })
    }

    private fun getMyCityWeatherForecastData(context: Context) {
        val retorfitBuilder  = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_API_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retorfitBuilder.getMyCityWeatherDataForecast(lat, lon)

        retrofitData.enqueue(object : Callback<CityWeatherForecast?> {
            override fun onResponse(
                call: Call<CityWeatherForecast?>,
                response: Response<CityWeatherForecast?>,
            ) {
                Log.i("FecthingForecastDataSucess", response.body().toString())
                val responseBody = response.body()!!
                weather_details_myCityWeatherForecast =responseBody
                cityWeatherForecastRc.layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL,false)
                cityWeatherForecastRc.adapter =  CitiyForecastWeatherListViewAdapter(context,R.layout.city_weather_forecast_list_item,weather_details_myCityWeatherForecast.list)
                loader.visibility = View.GONE
                mainContainer.visibility =View.VISIBLE
            }
            override fun onFailure(call: Call<CityWeatherForecast?>, t: Throwable) {
                loader.visibility = View.GONE
                mainContainer.visibility =View.VISIBLE
                Log.i("FecthingForecastDataError", "Failed")
            }
        })
    }


}