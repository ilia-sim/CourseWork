package ru.android.anyweather.dataClasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityData(
    val name : String,
    val latitude : Double,
    val longitude : Double,
    val country : String,
    val population : Int,
    val region : String,
    @SerialName("is_capital") val isCapital : Boolean
)
