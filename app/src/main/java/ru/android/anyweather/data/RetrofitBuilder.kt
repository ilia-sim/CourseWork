package ru.android.anyweather.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

abstract class RetrofitBuilder {
    companion object {
        private const val METEO_URL = "https://api.open-meteo.com"

        private const val NINJA_URL = "https://api.api-ninjas.com"

        private const val ALLCITIES_URL = "https://www.apicountries.com"

        lateinit var retrofitMeteo : Retrofit

        lateinit var retrofitNinja : Retrofit

        lateinit var retrofitAllCities : Retrofit

        fun initialize(){
            Utils.log("Started initializing Retrofit objects")

            retrofitMeteo = Retrofit
                .Builder()
                .baseUrl(METEO_URL)
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .build()

            retrofitNinja = Retrofit
                .Builder()
                .baseUrl(NINJA_URL)
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .build()

            retrofitAllCities = Retrofit
                .Builder()
                .baseUrl(ALLCITIES_URL)
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .build()

            Utils.log("Successful initializing Retrofit objects!")
        }
    }
}