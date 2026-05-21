package ru.android.anyweather.composables.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import ru.android.anyweather.dataClasses.CityCardData
import ru.android.anyweather.dataClasses.CityData
import ru.android.anyweather.dataClasses.WeatherData
import ru.android.anyweather.viewModels.MainContentViewModel

@Composable
fun CityScreenContent(
    viewModel : MainContentViewModel = MainContentViewModel(),
    onBackClick : () -> Unit
){
    if (viewModel.currentCityCardData == null){
        viewModel.currentCityCardData = CityCardData(
            CityData(
                "ERROR",
                0.0,
                0.0,
                "",
                0,
                "",
                false
            ),
            WeatherData(
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                "",
                "",
                JsonObject(mapOf()),
                JsonObject(mapOf())
            )
        )
    }

    Text(
        viewModel.currentCityCardData!!.cityData.name
    )
    Text(viewModel.currentCityCardData!!.cityData.name)
    Text(
        (viewModel.currentCityCardData!!.weather.daily["temperature_2m_min"] as JsonArray)[0].toString() +
                (viewModel.currentCityCardData!!.weather.dailyUnits["temperature_2m_min"] as JsonArray)[0].toString()
    )
    Text(
        (viewModel.currentCityCardData!!.weather.daily["temperature_2m_max"] as JsonArray)[0].toString() +
                (viewModel.currentCityCardData!!.weather.dailyUnits["temperature_2m_max"] as JsonArray)[0].toString()
    )
    Button(
        onBackClick
    ) {
        Text("BACK") //TODO clear hardcode
    }
}