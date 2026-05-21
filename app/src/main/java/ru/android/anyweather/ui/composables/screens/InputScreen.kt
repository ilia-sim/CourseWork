package ru.android.anyweather.ui.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.android.anyweather.R
import ru.android.anyweather.ui.viewModels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(
    viewModel : MainViewModel,
    goToCardsScreen : () -> Unit
){
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.medium_padding)),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier
                .weight(9f)
        ){
            Text(
                stringResource(R.string.input_screen_header)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = uiState.inputTextField,
                onValueChange = { viewModel.onTextChanged(it) },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.addSelectedCity(uiState.inputTextField)
                        focusManager.clearFocus()
                    }
                ),
                trailingIcon = {
                    IconButton(
                        enabled = uiState.inputTextField.isNotBlank(),
                        onClick = {
                            viewModel.addSelectedCity(uiState.inputTextField)
                        }
                    ){
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                        )
                    }
                },
//                textStyle = TextStyle(
//                    brush = remember {
//                        Brush.linearGradient(
//                            colors = listOf(
//                                Color.Red,
//                                Color.Blue,
//                                Color.White
//                            )
//                        )
//                    }
//                )
            )

            when{
                uiState.suggestionsList.isNotEmpty() -> LazyColumn {
                    //not reachable code!!!
                    items(uiState.suggestionsList){ cityName ->
                        Text(
                            cityName,
                            modifier = Modifier.clickable(
                                onClick = {
                                    viewModel.onSelectSuggestion(cityName)
                                }
                            )
                        )
                    }
                }
                uiState.selectedCitiesList.isNotEmpty() -> LazyColumn {
                    items(uiState.selectedCitiesList){ city ->
                        SelectedCity(
                            cityName = city,
                            onDelete = { viewModel.onSelectedCityDelete(city) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(R.dimen.small_padding))
                        )
                    }
                }
                else -> Text(
                    stringResource(R.string.empty_selected_cities_message)
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            onClick = {
                viewModel.onInputToCardsScreen()
                goToCardsScreen()
            },
            enabled = uiState.selectedCitiesList.isNotEmpty()
        ){
            Text(
                textAlign = TextAlign.Center,
                text=stringResource(R.string.get_weather_button_text)
            )
        }
    }
}

@Composable
fun SelectedCity(
    cityName : String,
    onDelete : () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(dimensionResource(R.dimen.small_padding)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            cityName
        )
        IconButton (
            onClick = onDelete,
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        }
    }
}