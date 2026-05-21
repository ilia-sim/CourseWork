package ru.android.anyweather.composables.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.json.JsonArray
import ru.android.anyweather.dataClasses.CityCardData
import ru.android.anyweather.dataClasses.CityData
import ru.android.anyweather.viewModels.MainContentViewModel

@Composable
fun CitiesListScreenContent(
    viewModel : MainContentViewModel = MainContentViewModel(),
    onCityCardClick : () -> Unit,
    onBackClick : () -> Unit
){
//    Log.w("MY_LOGGING", "ON CITIES LIST SCREEN")
//    Log.w("MY_LOGGING", cityCardData.toString())
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
//        viewModel.cityCardsList.forEach { cityCardData ->
//            Button(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                onClick = {
//                    viewModel.currentCityCardData = cityCardData as CityCardData?
//                    onCityCardClick()
//                }
//            ) {
//                Text(cityCardData.cityData.name)
//                Text(
//                    (cityCardData.weather.daily["temperature_2m_min"] as List<String>)[0] +
//                            (cityCardData.weather.dailyUnits["temperature_2m_min"] as List<String>)[0]
//                )
//                Text(
//                    (cityCardData.weather.daily["temperature_2m_max"] as List<String>)[0].toString() +
//                            (cityCardData.weather.dailyUnits["temperature_2m_max"] as List<String>)[0].toString()
//                )
//            }
//        }
        Button(
            onClick = {
                viewModel.currentCityCardData = null
                onBackClick()
            }
        ){
            Text("CITIES_LIST_BACK") //TODO clear hardcode
        }
    }
}