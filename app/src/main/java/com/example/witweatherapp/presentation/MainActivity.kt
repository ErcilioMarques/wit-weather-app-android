package com.example.witweatherapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.witweatherapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val homeWeatherFragment = HomeWeather()
    private val citiesWeatherFragment = CitiesWeatherList()
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homeWeatherFragment)

        bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home_menu -> replaceFragment(homeWeatherFragment)
                R.id.cities_weather_list_menu -> replaceFragment(citiesWeatherFragment)
            }
            true
        }

    }

    private  fun replaceFragment(fragment: Fragment){
        if(fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}