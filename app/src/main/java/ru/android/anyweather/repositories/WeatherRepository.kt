package ru.android.anyweather.repositories

import android.util.Log
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import retrofit2.HttpException
import ru.android.anyweather.RETROFIT_METEO
import ru.android.anyweather.dataClasses.CoordinatesData
import ru.android.anyweather.dataClasses.WeatherData
import ru.android.anyweather.dataSources.CityRemoteDataSource
import ru.android.anyweather.dataSources.WeatherRemoteDataSource
import java.lang.Exception

class WeatherRepository(
    weatherRemoteDataSourceClass: Class<WeatherRemoteDataSource> = WeatherRemoteDataSource::class.java
) : CityRepository(
    cityRemoteDataSourceClass = CityRemoteDataSource::class.java
){
    val retrofitWeatherService by lazy{
        RETROFIT_METEO.create(weatherRemoteDataSourceClass)
    }

    suspend fun getWeatherData(
        coordinatesData: CoordinatesData
    ) : WeatherData {
        try {
            val data = retrofitWeatherService.fetchWeatherData(
                coordinatesData.latitude,
                coordinatesData.longitude
            )
            Log.w("MY_LOGGING", data.latitude.toString())
            return data
        } catch(e : Exception){
            e.message?.let {
                if (it.isEmpty()) {
                    Log.e("MY_LOGGING", "Empty error message")
                }else{
                    Log.e("MY_LOGGING", it)
                }
            }
            return WeatherData(
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                "",
                "",
                JsonObject(mapOf()),
                JsonObject(mapOf())
            )
        }
    }
}