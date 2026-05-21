package ru.android.anyweather.ui.viewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonArray
import ru.android.anyweather.R
import ru.android.anyweather.data.Utils
import ru.android.anyweather.data.dataClasses.CityCardData
import ru.android.anyweather.data.dataClasses.DayWeatherData
import ru.android.anyweather.data.repositories.AllCitiesRepository
import ru.android.anyweather.data.repositories.CityRepository
import ru.android.anyweather.data.repositories.WeatherRepository
import java.time.LocalDate

data class MainUiState(
    val inputTextField : String = "",
    val suggestionsList : List<String> = emptyList(),
    val selectedCitiesList : List<String> = emptyList(),
    val cityCardsList : List<CityCardData> = emptyList(),
    val isCityCardsLoading : Boolean = false,
    val selectedCityCard : CityCardData? = null,
    val errorMessage : String? = null,
)

class MainViewModel(
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository,
    private val allCitiesRepository: AllCitiesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    private val _notificationsFlow = MutableSharedFlow<Int>()
    val notificationsFlow: SharedFlow<Int> = _notificationsFlow.asSharedFlow()

    init{
        Utils.log("Started initializing MainViewModel")

//        viewModelScope.launch {
//            _uiState
//                .map { it.inputTextField }
//                .filter { it.isNotBlank() }
//                .collect {
//                    loadSuggestions(it)
//                }
//        }

        Utils.log("Successful initializing MainViewModel!")
    }

    fun onTextChanged(newValue : String){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    inputTextField = newValue
                )
            }
        }
    }

    @Suppress("unused")
    fun loadSuggestions(query : String){
        Utils.log("Loading suggestions...")
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    suggestionsList = allCitiesRepository.getAllCitiesNames()
                )
            }
        }
        Utils.log("Successful suggestions loading!")
    }

    fun clearSuggestions(){
        Utils.log("Clearing suggestions...")
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    suggestionsList = emptyList()
                )
            }
        }
        Utils.log("Successful suggestions clearing!")
    }

    fun onSelectedCityDelete(cityName : String){
        Utils.log("Deleting selected city ${cityName}...")
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    selectedCitiesList = _uiState.value.selectedCitiesList.filter { it != cityName }
                )
            }
            Utils.log("Successful city deleting!")
        }
    }

    fun onSelectSuggestion(cityName : String){
        clearInputTextField()
        clearSuggestions()
        addSelectedCity(cityName)
    }

    fun showNotification(@StringRes notificationRes : Int){
        viewModelScope.launch {
            _notificationsFlow.emit(notificationRes)
        }
    }

    fun addSelectedCity(query : String){
        Utils.log("Adding city ${query}...")
        if (query.isBlank()){
            showNotification(R.string.notification_cant_enter_empty_string)
            return
        }
        viewModelScope.launch {
            _uiState.value.selectedCitiesList.let{
                if (it.contains(query)){
                    Utils.log("Fail! City was already added")
                    showNotification(R.string.notification_city_already_added)
                    return@launch
                }
            }
            _uiState.update {
                it.copy(
                    selectedCitiesList = buildList {
                        addAll(_uiState.value.selectedCitiesList)
                        add(query)
                    }
                )
            }
        }
        clearInputTextField()
    }

    fun clearInputTextField(){
        Utils.log("Clearing text field...")
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    inputTextField = ""
                )
            }
            Utils.log("Successful clearing text field!")
        }
    }

    fun getCurrentTemperature(cityCardData: CityCardData) : String{
        Utils.log("Getting current temperature...")
        try {
            if (cityCardData.weatherData == null) {
                showNotification(R.string.notification_error_getting_current_temp)
                return ""
            }
            val res = """${doubleArrayOf(
                cityCardData.weatherData.daily["temperature_2m_max"]!!.jsonArray[0].toString().toDouble(),
                cityCardData.weatherData.daily["temperature_2m_min"]!!.jsonArray[0].toString().toDouble()
            ).average()} ${cityCardData.weatherData.dailyUnits["temperature_2m_max"].toString().drop(1).dropLast(1)}"""
            Utils.log("Successful getting current temperature!")
            return res
        }catch (e : Exception){
            Utils.log("Failed getting current temperature! Error message: ${e.message}")
            showNotification(R.string.notification_error_getting_current_temp)
            return ""
        }
    }

    @StringRes
    fun getCurrentWeather(cityCardData: CityCardData) : Int{
        Utils.log("Getting current weather...")
        try {
            val res = Utils.parseWMO(cityCardData.weatherData!!.daily["weather_code"]!!.jsonArray[0].toString())
            Utils.log("Successful getting current weather!")
            return res
        }
        catch (e : Exception){
            Utils.log("Failed getting current weather! Error message: ${e.message}")
            showNotification(R.string.notification_error_getting_current_weather)
            return Utils.parseWMO("undefined")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeekWeatherData(cityCardData: CityCardData) : List<DayWeatherData>{
        Utils.log("Getting week weather data...")
        try{
            var ld : LocalDate
            val res = buildList {
                cityCardData.weatherData!!.daily["time"]!!.jsonArray.forEachIndexed { index, item ->
                    ld = LocalDate.parse(item.toString().drop(1).dropLast(1))

                    add(
                        DayWeatherData(
                            dayNumber = ld.dayOfMonth.toString(),
                            month = Utils.parseMonth(ld.monthValue),
                            weatherStatus = Utils.parseWMO(cityCardData.weatherData.daily["weather_code"]!!.jsonArray[index].toString()),
                            temperature = """${doubleArrayOf(
                                cityCardData.weatherData.daily["temperature_2m_max"]!!.jsonArray[index].toString().toDouble(),
                                cityCardData.weatherData.daily["temperature_2m_min"]!!.jsonArray[index].toString().toDouble()
                            ).average()} ${cityCardData.weatherData.dailyUnits["temperature_2m_max"].toString().drop(1).dropLast(1)}"""
                        )
                    )
                }
            }
            Utils.log("Successful getting week weather data! $res")
            return res
        }
        catch (e : Exception){
            Utils.log("Failed getting week weather data! Error message: ${e.message}")
            showNotification(R.string.notification_error_getting_week_weather)
            return emptyList()
        }
    }

    fun onInputToCardsScreen(){
        Utils.log("Going from Input Screen to Cards Screen...")
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isCityCardsLoading = true
                )
            }

            val list = buildList {
                _uiState.value.selectedCitiesList.forEach { cityName ->
                    add(
                        cityRepository.getCityData(cityName).let {
                            if (it == null){
                                CityCardData()
                            }else{
                                CityCardData(
                                    cityData = it,
                                    weatherData = weatherRepository.getWeatherData(it)
                                )
                            }
                        }
                    )
                }
            }

            _uiState.update {
                it.copy(
                    inputTextField = "",
                    selectedCitiesList = emptyList(),
                    suggestionsList = emptyList(),
                    cityCardsList = list,
                    isCityCardsLoading = false
                )
            }
            Utils.log("Successful city cards loading!")
        }
    }

    fun onCardsToInputScreen(){
        Utils.log("Going from Cards Screen to Input Screen...")
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    cityCardsList = emptyList()
                )
            }
            Utils.log("Successful city cards clearing!")
        }
    }

    fun onCardsToWeatherScreen(cityCardData: CityCardData){
        Utils.log("Going from Cards Screen to Weather Screen...")
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectedCityCard = cityCardData
                )
            }
            Utils.log("Successful setting selectedCityCard!")
        }
    }

    fun onWeatherToCardsScreen(){
        Utils.log("Going from Weather Screen to Cards Screen...")
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectedCityCard = null
                )
            }
            Utils.log("Successful clearing selectedCityCard!")
        }
    }

//    fun loadScreenRoute(screenRoute: ScreenRoute){
//        Utils.log("Loading screen ${screenRoute.name}...")
//        try {
//            when (screenRoute) {
//                ScreenRoute.INPUT_SCREEN -> onInputScreenLoad()
//                ScreenRoute.CARDS_SCREEN -> onCardsScreenLoad()
//                ScreenRoute.WEATHER_SCREEN -> onWeatherScreenLoad()
//            }
//            Utils.log("Successful screen loading!")
//        } catch (e : Error){
//            Utils.log("Screen loading failed! Error message: ${e.message}")
//            _uiState.update{
//                it.copy(
//                    errorMessage = e.message,
//                    isCityCardsLoading = false
//                )
//            }
//        }
//    }

    class Factory(
        private val cityRepository: CityRepository,
        private val weatherRepository: WeatherRepository,
        private val allCitiesRepository: AllCitiesRepository
    ) : ViewModelProvider.Factory{

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(
                cityRepository,
                weatherRepository,
                allCitiesRepository
            ) as T
        }
    }
}