package ru.android.anyweather.data.dataClasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class WeatherData(
    val latitude : Double,
    val longitude : Double,
    val elevation : Double,
    @SerialName("generationtime_ms") val generationTimeMs : Double,
    @SerialName("utc_offset_seconds") val utcOffsetSeconds : Double,
    val timezone : String,
    @SerialName("timezone_abbreviation") val timezoneAbbr : String,
    @SerialName("daily_units") val dailyUnits : JsonObject,
    val daily : JsonObject
)