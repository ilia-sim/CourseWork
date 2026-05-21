package ru.android.anyweather.ui.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import ru.android.anyweather.R
import ru.android.anyweather.data.dataClasses.CityCardData
import ru.android.anyweather.ui.viewModels.MainViewModel

@Composable
fun CityCardsScreen(
    viewModel : MainViewModel,
    goToWeatherScreen : () -> Unit,
    goToInputScreen : () -> Unit
){
    val uiState by viewModel.uiState.collectAsState()

    when{
        !uiState.errorMessage.isNullOrBlank() -> Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                uiState.errorMessage!!
            )
            Button(
                onClick = {
                    viewModel.onCardsToInputScreen()
                    goToInputScreen()
                }
            ){
                Text(
                    stringResource(R.string.back_button_text)
                )
            }
        }

        uiState.isCityCardsLoading -> Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }

        else -> Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.medium_padding)),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(9f)
            ) {
                items(uiState.cityCardsList){ cityCard ->
                    CityCard(
                        viewModel = viewModel,
                        cityCardData = cityCard,
                        onClick = {
                            viewModel.onCardsToWeatherScreen(cityCard)
                            goToWeatherScreen()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.medium_padding))
                    )
                    Spacer(Modifier.height(dimensionResource(R.dimen.medium_spacing)))
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = {
                    viewModel.onCardsToInputScreen()
                    goToInputScreen()
                }
            ){
                Text(
                    stringResource(R.string.reset_button_text)
                )
            }
        }
    }
}

@Composable
fun CityCard(
    viewModel: MainViewModel,
    cityCardData : CityCardData,
    onClick : () -> Unit,
    modifier : Modifier = Modifier,
){
    when{
        cityCardData.cityData == null || cityCardData.weatherData == null -> Column(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .padding(dimensionResource(R.dimen.small_padding))
            ){
                Text(
                    stringResource(R.string.city_card_null_message)
                )
            }

        else -> Column(
            modifier = modifier
                .clickable(
                    onClick = onClick
                )
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(dimensionResource(R.dimen.small_padding))
        ){
            Text(
                cityCardData.cityData.name
            )
            Text(
                viewModel.getCurrentTemperature(cityCardData)
            )
            Text(
                stringResource(viewModel.getCurrentWeather(cityCardData))
            )
        }
    }
}