package ru.android.anyweather.data.repositories

import retrofit2.Retrofit
import ru.android.anyweather.data.Utils
import ru.android.anyweather.data.dataSources.AllCitiesRemoteDataSource

class AllCitiesRepository(
    retrofit: Retrofit,
    allCitiesRemoteDataSourceClass: Class<AllCitiesRemoteDataSource>
) {
    private val retrofitService = retrofit.create(allCitiesRemoteDataSourceClass)

    suspend fun getAllCitiesNames() : List<String>{
        try{
            Utils.log("Loading data at all cities repository...")
            val data = retrofitService.fetchAllCities()
            Utils.log("Successful data loading at all cities repository!")
            return data
        }catch (e : Exception){
            Utils.log("Failed to get data at all cities repository! Error message: ${e.message}")
            return emptyList()
        }
    }
}