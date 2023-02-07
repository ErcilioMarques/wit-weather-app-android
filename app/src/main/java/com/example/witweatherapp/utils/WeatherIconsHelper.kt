package com.example.witweatherapp.utils

import androidx.annotation.DrawableRes
import com.example.witweatherapp.R

sealed class WeatherIconsHelper(
    val iconCode: String,
    @DrawableRes val iconRes: Int
) {


    companion object {

        object ClearSky : WeatherIconsHelper(
            iconCode = "01d",
            iconRes = R.drawable.d10
        )
        object MainlyClear : WeatherIconsHelper(
            iconCode = "01n",
            iconRes = R.drawable.n10
        )
        object PartlyCloudy : WeatherIconsHelper(
            iconCode = "02d",
            iconRes = R.drawable.d20
        )
        object Overcast : WeatherIconsHelper(
            iconCode = "02n",
            iconRes = R.drawable.n20
        )
        object Foggy : WeatherIconsHelper(
            iconCode = "03d",
            iconRes = R.drawable.d30
        )
        object DepositingRimeFog : WeatherIconsHelper(
            iconCode = "03n",
            iconRes = R.drawable.n30
        )
        object LightDrizzle : WeatherIconsHelper(
            iconCode = "04d",
            iconRes = R.drawable.d40
        )
        object ModerateDrizzle : WeatherIconsHelper(
            iconCode = "04n",
            iconRes = R.drawable.n40
        )
        object DenseDrizzle : WeatherIconsHelper(
            iconCode = "09d",
            iconRes = R.drawable.d90
        )
        object LightFreezingDrizzle : WeatherIconsHelper(
            iconCode = "09n",
            iconRes = R.drawable.n90
        )
        object DenseFreezingDrizzle : WeatherIconsHelper(
            iconCode = "10d",
            iconRes = R.drawable.d100
        )
        object SlightRain : WeatherIconsHelper(
            iconCode = "10n",
            iconRes = R.drawable.n100
        )
        object ModerateRain : WeatherIconsHelper(
            iconCode = "11d",
            iconRes = R.drawable.d11
        )
        object HeavyRain : WeatherIconsHelper(
            iconCode = "11n",
            iconRes = R.drawable.n11
        )
        object HeavyFreezingRain: WeatherIconsHelper(
            iconCode = "13d",
            iconRes = R.drawable.d13
        )
        object SlightSnowFall: WeatherIconsHelper(
            iconCode = "13n",
            iconRes = R.drawable.n13
        )
        object ModerateSnowFall: WeatherIconsHelper(
            iconCode = "50d",
            iconRes = R.drawable.d50
        )
        object HeavySnowFall: WeatherIconsHelper(
            iconCode = "50n",
            iconRes = R.drawable.n50
        )

        var iconList: List<WeatherIconsHelper> = listOf( ClearSky,
            MainlyClear, PartlyCloudy
            ,Overcast
            ,Foggy
            ,DepositingRimeFog
            ,LightDrizzle
            ,ModerateDrizzle
            ,DenseDrizzle
            ,LightFreezingDrizzle
            ,DenseFreezingDrizzle
            ,SlightRain
            ,ModerateRain
            ,HeavyRain
            ,LightFreezingDrizzle
            ,HeavyFreezingRain
            ,SlightSnowFall
            ,ModerateSnowFall
            ,HeavySnowFall)

        fun fromWMO(code: String): WeatherIconsHelper? {
            return iconList.find { it.iconCode==code }
        }
    }
}