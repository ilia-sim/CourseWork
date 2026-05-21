package ru.android.anyweather.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.android.anyweather.dataClasses.CityCardData
import ru.android.anyweather.dataClasses.CityData
import ru.android.anyweather.dataClasses.CoordinatesData
import ru.android.anyweather.repositories.CityRepository
import ru.android.anyweather.repositories.WeatherRepository

class MainContentViewModel(
    private val cityRepository: CityRepository = CityRepository(),
    private val weatherRepository: WeatherRepository = WeatherRepository()
) : ViewModel() {
    var textField by mutableStateOf("DEFAULT_VALUE")
    var citiesList = mutableStateListOf<String>()
    var cityCardsList = mutableListOf<CityCardData>()
    var currentCityCardData : CityCardData? = null

    fun onTextChanged(newValue : String){
        textField = newValue
    }

    fun onCheckWeather(){
        viewModelScope.launch {
            var cityData : CityData
            citiesList.forEach { cityName ->

                cityData = cityRepository.getCityData(cityName)
                cityCardsList.add(
                    CityCardData(
                        cityData,
                        weatherRepository.getWeatherData(
                            CoordinatesData(cityData.latitude, cityData.longitude)
                        )
                    )
                )
                Log.w("MY_LOGGING", cityCardsList.last().toString())
            }
        }
    }

    fun onCityConfirm(){
        if (textField.isNotBlank()) {
            citiesList.add(textField)
            textField = ""
        }
        //TODO show notification that empty strings are not allowed
    }

    fun deleteCity(city : String){
        citiesList.remove(city)
    }
}