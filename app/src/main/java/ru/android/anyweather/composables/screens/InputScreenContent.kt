package ru.android.anyweather.composables.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.android.anyweather.viewModels.MainContentViewModel

@Composable
fun InputScreenContent(
    viewModel : MainContentViewModel = MainContentViewModel(),
    onCheckWeather: () -> Unit
){
    Column(
        modifier = Modifier
            .padding(
                start = 15.dp,
                end = 15.dp,
                bottom = 15.dp, //TODO clear hardcode
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        //TOP SIDE OF SCREEN
        Column(
            modifier = Modifier
                .weight(10f),
            verticalArrangement = Arrangement.Top
        ){

            //INPUT FIELD
            Row(

            ) {
                TextField(
                    modifier = Modifier
                        .weight(3f),
                    value = viewModel.textField,
                    onValueChange = { viewModel.onTextChanged(it) },
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier
                        .width(15.dp) //TODO clear hardcode
                )

                Button(
                    modifier = Modifier
                        .weight(1f),
                    onClick = { viewModel.onCityConfirm() }
                ) {
                    Text("E") //TODO clear hardcode
                }
            }

            Spacer(
                modifier = Modifier
                    .height(15.dp) //TODO clear hardcode
            )

            //CITIES LIST
            Column(

            ){
                viewModel.citiesList.forEach{ city ->
                    Column(

                    ) {
                        Spacer(
                            modifier = Modifier.height(5.dp) //TODO clear hardcode
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(city)
                            Button(
                                onClick = { viewModel.deleteCity(city) }
                            ) {
                                Text(text = "DELETE_CITY") //TODO clear hardcode
                            }
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier
                .weight(0.5f)
        )

        //BOTTOM SIDE OF SCREEN
        Button(
            modifier = Modifier
                .weight(1f),
            onClick = {
                viewModel.onCheckWeather()
                onCheckWeather()
            }
        ){
            Text(
                text="CHECK_WEATHER_TEXT" //TODO clear hardcode
            )
        }
    }
}