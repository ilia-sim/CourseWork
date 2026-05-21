package ru.android.anyweather

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.android.anyweather.data.RetrofitBuilder
import ru.android.anyweather.data.Utils
import ru.android.anyweather.data.dataSources.AllCitiesRemoteDataSource
import ru.android.anyweather.data.dataSources.CityRemoteDataSource
import ru.android.anyweather.data.dataSources.WeatherRemoteDataSource
import ru.android.anyweather.data.repositories.AllCitiesRepository
import ru.android.anyweather.data.repositories.CityRepository
import ru.android.anyweather.data.repositories.WeatherRepository
import ru.android.anyweather.ui.composables.MainContent
import ru.android.anyweather.ui.theme.AnyWeatherTheme
import ru.android.anyweather.ui.viewModels.MainViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RetrofitBuilder.initialize()

        Utils.log("Started initializing repositories")

        val cityRepository = CityRepository(
            RetrofitBuilder.retrofitNinja,
            CityRemoteDataSource::class.java
        )

        val weatherRepository = WeatherRepository(
            RetrofitBuilder.retrofitMeteo,
            WeatherRemoteDataSource::class.java
        )

        val allCitiesRepository = AllCitiesRepository(
            RetrofitBuilder.retrofitAllCities,
            AllCitiesRemoteDataSource::class.java
        )

        Utils.log("Successful initializing repositories!")

        enableEdgeToEdge()
        setContent {
            AnyWeatherTheme {
                val viewModel : MainViewModel = viewModel(
                    factory = MainViewModel.Factory(
                        cityRepository,
                        weatherRepository,
                        allCitiesRepository
                    )
                )
                MainContent(viewModel)
            }
        }
    }
}