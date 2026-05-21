package ru.android.anyweather.data.repositories

import retrofit2.Retrofit
import ru.android.anyweather.data.Utils
import ru.android.anyweather.data.dataClasses.CityData
import ru.android.anyweather.data.dataSources.CityRemoteDataSource
import java.lang.Exception

class CityRepository(
    retrofit: Retrofit,
    cityRemoteDataSourceClass : Class<CityRemoteDataSource>
) {
    private val retrofitService : CityRemoteDataSource = retrofit.create(cityRemoteDataSourceClass)

    suspend fun getCityData(
        cityName : String
    ) : CityData? {
        try {
            Utils.log("Loading data at city repository...")
            val data = retrofitService.fetchCityData(cityName)
            Utils.log("Successful data loading at city repository!")
            return data[0]
        }catch (e : Exception){
            Utils.log("Failed to get data at city repository! Error message: ${e.message}")
            return null
        }
    }
}