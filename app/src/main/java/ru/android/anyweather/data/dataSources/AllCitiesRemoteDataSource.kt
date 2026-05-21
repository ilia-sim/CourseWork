package ru.android.anyweather.data.dataSources

import retrofit2.http.GET

interface AllCitiesRemoteDataSource {
    @GET("UNDEFINED_API_ENDPOINT")
    suspend fun fetchAllCities() : List<String>
}