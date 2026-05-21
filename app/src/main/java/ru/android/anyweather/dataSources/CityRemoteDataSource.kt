package ru.android.anyweather.dataSources

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.android.anyweather.PrivateConfig
import ru.android.anyweather.dataClasses.CityData

interface CityRemoteDataSource {
    @GET("v1/city")
    suspend fun fetchCityData (
        @Query("name") cityName : String,
        @Header("X-Api-Key") apiKey : String = PrivateConfig.X_API_KEY
    ) : List<CityData>
}