package ru.android.anyweather.data.dataSources

import retrofit2.http.GET
import retrofit2.http.Query
import ru.android.anyweather.data.dataClasses.WeatherData

interface WeatherRemoteDataSource{

    @GET("v1/forecast")
    suspend fun fetchWeatherData(
        @Query("latitude") cityLatitude : Double,
        @Query("longitude") cityLongitude : Double,
        @Query("timezone") timezone : String = "auto",
        @Query("daily") weatherCodeDailyParam : String = "weather_code",
        @Query("daily") tempMaxDailyParam : String = "temperature_2m_max",
        @Query("daily") tempMinDailyParam : String = "temperature_2m_min",
    ) : WeatherData
}