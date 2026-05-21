package ru.android.anyweather.dataSources

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.android.anyweather.dataClasses.WeatherData

interface WeatherRemoteDataSource{

    @GET("v1/forecast")
    suspend fun fetchWeatherData(
        @Query("latitude") cityLatitude : Double,
        @Query("longitude") cityLongitude : Double,
        @Query("daily") weatherCodeDailyParam : String = "weather_code",
        @Query("daily") tempMaxDailyParam : String = "temperature_2m_max",
        @Query("daily") tempMinDailyParam : String = "temperature_2m_min",
    ) : WeatherData
}