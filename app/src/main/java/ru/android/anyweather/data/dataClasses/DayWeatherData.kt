package ru.android.anyweather.data.dataClasses

import androidx.annotation.StringRes

data class DayWeatherData (
    val dayNumber : String,
    @param:StringRes val month : Int,
    val temperature : String,
    @param:StringRes val weatherStatus : Int,
)