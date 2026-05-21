package ru.android.anyweather.repositories

import android.util.Log
import retrofit2.HttpException
import ru.android.anyweather.RETROFIT_NINJA
import ru.android.anyweather.dataClasses.CityData
import ru.android.anyweather.dataClasses.CoordinatesData
import ru.android.anyweather.dataSources.CityRemoteDataSource
import java.io.IOException
import java.lang.Exception
import java.lang.IndexOutOfBoundsException

open class CityRepository(
    cityRemoteDataSourceClass : Class<CityRemoteDataSource> = CityRemoteDataSource::class.java
) {
    protected val retrofitCityService by lazy{
        RETROFIT_NINJA.create(cityRemoteDataSourceClass)
    }

    suspend fun getCityCoordinates(
        cityName : String
    ) : CoordinatesData {
        try {
            val data = retrofitCityService.fetchCityData(cityName)
            Log.i("MY_LOGGING", "Coordinates of ${cityName}: ${data[0].latitude}, ${data[0].longitude}")
            return CoordinatesData(data[0].latitude, data[0].longitude)
        }catch (e : IOException){
            e.message?.let {
                if (it.isEmpty()) {
                    Log.e("MY_LOGGING", "Empty error message")
                }else{
                    Log.e("MY_LOGGING", it)
                }
            }
            return CoordinatesData(-1.0,-1.0)
        }
    }

    suspend fun getCityData(
        cityName : String
    ) : CityData {
        try {
            val data = retrofitCityService.fetchCityData(cityName)
            //Log.i("MY_LOGGING", "Coordinates of ${cityName}: ${data[0].latitude}, ${data[0].longitude}")
            return data[0]
        }catch (e : Exception){
            e.message?.let {
                if (it.isEmpty()) {
                    Log.e("MY_LOGGING", "Empty error message")
                }else{
                    Log.e("MY_LOGGING", it)
                }
            }
            return CityData(
                "",0.0,0.0,"",0,"",false
            )
        }
    }
}