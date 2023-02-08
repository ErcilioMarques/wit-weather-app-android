package com.example.witweatherapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import com.example.witweatherapp.BuildConfig.BASE_API_URL
import com.example.witweatherapp.R
import com.example.witweatherapp.models.CitiesWeather
import com.example.witweatherapp.models.CityWeather
import com.example.witweatherapp.repository.ApiInterface
import com.example.witweatherapp.utils.CitiesListViewAdapter
import com.example.witweatherapp.utils.RetrofitBuilder.Companion.retrofitBuilder
import com.example.witweatherapp.utils.RetrofitBuilder.Companion.weatherApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.fragment_cities_weather_list.view.*
import kotlinx.android.synthetic.main.fragment_home_weather.view.*




/**
 * A simple [Fragment] subclass.
 * Use the [CitiesWeatherList.newInstance] factory method to
 * create an instance of this fragment.
 */
class CitiesWeatherList : Fragment() {

    lateinit private var listView: ListView
    lateinit private var  listLoader: ProgressBar

    private var list = mutableListOf<CityWeather>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_cities_weather_list, container, false)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getCitiesWeatherData(context)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = requireView().listView
        listLoader =view.list_view_loader
        listView.setOnItemClickListener{ parent: AdapterView<*>, view: View, position:Int, id:Long->

            val intent= Intent(activity, CityWeatherDetailsActivity::class.java)
            intent.putExtra("lat", list.get(position).coord.lat.toString())
            intent.putExtra("lon", list.get(position).coord.lon.toString())

            startActivity(intent)
        }
    }

    private fun getCitiesWeatherData(context: Context) {
        val citiesIds = "2267057,3117735,2988507,2950159,2618425,3169070,2643743,2964574,3067696,2761369"
        val retrofitData = weatherApi().getCitiesWeatherData(citiesIds)

        retrofitData.enqueue(object : Callback<CitiesWeather?> {
            override fun onResponse(
                call: Call<CitiesWeather?>,
                response: Response<CitiesWeather?>,
            ) {
                val responseBody = response.body()!!

                list.removeAll(list)
                for (myData in responseBody.list){
                    list.add(myData)
                }

                listView.visibility = View.VISIBLE
                listLoader.visibility = View.GONE
                listView.adapter =  CitiesListViewAdapter(context,R.layout.city_weather_list_item,list)            }

            override fun onFailure(call: Call<CitiesWeather?>, t: Throwable) {
                Toast.makeText(context, "Something went wrong, please try again!",Toast.LENGTH_LONG).show()
            }
        })
    }
}