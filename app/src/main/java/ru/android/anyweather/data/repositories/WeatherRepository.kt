package ru.android.anyweather.data.repositories

import retrofit2.Retrofit
import ru.android.anyweather.data.Utils
import ru.android.anyweather.data.dataClasses.CityData
import ru.android.anyweather.data.dataClasses.WeatherData
import ru.android.anyweather.data.dataSources.WeatherRemoteDataSource
import java.lang.Exception

class WeatherRepository(
    retrofit: Retrofit,
    weatherRemoteDataSourceClass: Class<WeatherRemoteDataSource>
){
    private val retrofitService : WeatherRemoteDataSource = retrofit.create(weatherRemoteDataSourceClass)

    suspend fun getWeatherData(
        cityData: CityData?
    ) : WeatherData? {
        try {
            Utils.log("Loading data at weather repository...")
            if (cityData == null){
                throw Exception("cityData is null")
            }
            val data = retrofitService.fetchWeatherData(
                cityData.latitude,
                cityData.longitude
            )
            Utils.log("Successful data loading at weather repository!")
            Utils.log("Weather data = $data")
            return data
        } catch(e : Exception){
            Utils.log("Failed get city data in city repository! Error message: ${e.message}")
            return null
        }
    }
}