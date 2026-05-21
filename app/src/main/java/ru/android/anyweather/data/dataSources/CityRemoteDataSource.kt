package ru.android.anyweather.data.dataSources

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.android.anyweather.PrivateConfig
import ru.android.anyweather.data.dataClasses.CityData

interface CityRemoteDataSource {
    @GET("v1/city")
    suspend fun fetchCityData (
        @Query("name") cityName : String,
        @Header("X-Api-Key") apiKey : String = PrivateConfig.X_API_KEY
    ) : List<CityData>
}