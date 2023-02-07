package com.example.witweatherapp.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.witweatherapp.R
import com.example.witweatherapp.models.CitiesWeather
import com.example.witweatherapp.models.CityWeather
import com.example.witweatherapp.models.Weather
import com.example.witweatherapp.models.forecast.CityWeatherForecast
import com.example.witweatherapp.repository.ApiInterface
import com.example.witweatherapp.utils.CitiesListViewAdapter
import com.example.witweatherapp.utils.CitiyForecastWeatherListViewAdapter
import com.example.witweatherapp.utils.WeatherIconsHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.fragment_home_weather.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * A simple [Fragment] subclass.
 * Use the [HomeWeather.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeWeather : Fragment() {

    lateinit private var myCityWeather: CityWeather
    lateinit private var myCityWeatherForecast: CityWeatherForecast
    lateinit private var cityWeatherForecastRc: RecyclerView
    lateinit private var  loader:ProgressBar
    lateinit private var mainContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_weather, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMyCityWeatherData(requireContext())
        cityWeatherForecastRc = view.city_forecast_weather_rc
        loader =view.loader
        mainContainer =view.mainContainer
        getMyCityWeatherForecastData(requireContext())
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun fillTheUI(view: View) {
        view.city.text = myCityWeather.sys.country
        view.country.text = myCityWeather.name

        view.weather_icon.setImageDrawable(WeatherIconsHelper.fromWMO(myCityWeather.weather[0].icon)
            ?.let { resources.getDrawable(it.iconRes) })
        view.status.text = myCityWeather.weather[0].main.capitalize()
        view.temp.text =   myCityWeather.main.temp.toString() + "ºC"
        view.temp_min.text ="Min Temp: " +  myCityWeather.main.temp_min.toString()+ "ºC"
        view.temp_max.text ="Max Temp: " +  myCityWeather.main.temp_max.toString()+ "ºC"
        view.sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
            Date(myCityWeather.sys.sunrise.toLong() * 1000)
        )
        view.sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
            Date(myCityWeather.sys.sunset.toLong() * 1000)
        )
        view.wind.text = myCityWeather.wind.speed.toString()
        view.pressure.text = myCityWeather.main.pressure.toString()
        view.humidity.text = myCityWeather.main.humidity.toString()
        view.feels_like.text = myCityWeather.main.feels_like.toString()+ "ºC"
    }

    private fun getMyCityWeatherData(context: Context) {
        val retorfitBuilder  = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retorfitBuilder.getMyCityWeatherData()

        retrofitData.enqueue(object : Callback<CityWeather?> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<CityWeather?>, response: Response<CityWeather?>) {
                Log.i("FecthingDataSucess", response.body().toString())
                val responseBody = response.body()!!
                myCityWeather =responseBody

                fillTheUI(view!!)
            }

            override fun onFailure(call: Call<CityWeather?>, t: Throwable) {
                Log.i("FecthingDataFailure", "Failed")
            }
        })
    }

    private fun getMyCityWeatherForecastData(context: Context) {
        val retorfitBuilder  = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retorfitBuilder.getMyCityWeatherDataForecast()

        retrofitData.enqueue(object : Callback<CityWeatherForecast?> {
            override fun onResponse(
                call: Call<CityWeatherForecast?>,
                response: Response<CityWeatherForecast?>,
            ) {
                Log.i("FecthingForecastDataSucess", response.body().toString())
                val responseBody = response.body()!!
                myCityWeatherForecast =responseBody
                cityWeatherForecastRc.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                cityWeatherForecastRc.adapter =  CitiyForecastWeatherListViewAdapter(context,R.layout.city_weather_forecast_list_item,myCityWeatherForecast.list)
                loader.visibility = View.GONE
                mainContainer.visibility =View.VISIBLE
            }
            override fun onFailure(call: Call<CityWeatherForecast?>, t: Throwable) {
                Log.i("FecthingForecastDataError", "Failed")
            }
        })
    }

}