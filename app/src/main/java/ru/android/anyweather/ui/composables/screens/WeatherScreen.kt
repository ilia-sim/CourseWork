package ru.android.anyweather.ui.composables.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.android.anyweather.R
import ru.android.anyweather.data.dataClasses.DayWeatherData
import ru.android.anyweather.ui.viewModels.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(
    viewModel : MainViewModel,
    goToCardsScreen : () -> Unit
){
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.medium_padding))
    ){
        when (uiState.selectedCityCard) {
            null -> Text(
                modifier = Modifier
                    .weight(9f),
                textAlign = TextAlign.Start,
                text = stringResource(R.string.weather_loading_error_message)
            )

            else -> {
                Column(
                    modifier = Modifier
                        .weight(9f)
                ) {
                    val cardData = uiState.selectedCityCard!!
                    Text(
                        text = cardData.cityData!!.name,
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize
                    )
                    Spacer(Modifier.height(dimensionResource(R.dimen.large_spacing)))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(viewModel.getWeekWeatherData(cardData)) { dayWeatherData ->
                            WeatherBlock(dayWeatherData)
                            Spacer(
                                Modifier.height(dimensionResource(R.dimen.medium_spacing))
                            )
                        }
                    }
                }
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            onClick = {
                viewModel.onWeatherToCardsScreen()
                goToCardsScreen()
            }
        ) {
            Text(stringResource(R.string.back_button_text))
        }
    }
}

@Composable
fun WeatherBlock(
    dayWeatherData: DayWeatherData,
    modifier : Modifier = Modifier
){
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onTertiary)
            .padding(dimensionResource(R.dimen.small_padding)),
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ){
                Text(
                    dayWeatherData.dayNumber
                )
                Spacer(Modifier.width(dimensionResource(R.dimen.medium_spacing)))
                Text(
                    stringResource(dayWeatherData.month)
                )
            }
            Spacer(Modifier.height(dimensionResource(R.dimen.medium_spacing)))
            Text(
                dayWeatherData.temperature,
            )
            Spacer(Modifier.height(dimensionResource(R.dimen.medium_spacing)))
            Text(
                stringResource(dayWeatherData.weatherStatus)
            )
        }
    }
}