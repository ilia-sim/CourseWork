package ru.android.anyweather.ui.composables

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.android.anyweather.R
import ru.android.anyweather.data.ScreenRoute
import ru.android.anyweather.ui.composables.screens.CityCardsScreen
import ru.android.anyweather.ui.composables.screens.WeatherScreen
import ru.android.anyweather.ui.composables.screens.InputScreen
import ru.android.anyweather.ui.viewModels.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    viewModel : MainViewModel
){
    val navController = rememberNavController()
    val click : (ScreenRoute) -> Unit = remember { { route : ScreenRoute -> navController.navigate(route.name) } }
    val goToCards = remember(click) { { click(ScreenRoute.CARDS_SCREEN) } }
    val goToWeather = remember(click) { { click(ScreenRoute.WEATHER_SCREEN) } }
    val goToInput = remember(click) { { click(ScreenRoute.INPUT_SCREEN) } }
    val localContext = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name)
                    )
                }
            )
        }
    ) { innerPadding ->
        LaunchedEffect(Unit) {
            viewModel.notificationsFlow.collect { message ->
                Toast.makeText(localContext, message, Toast.LENGTH_SHORT).show()
            }
        }
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.INPUT_SCREEN.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ScreenRoute.INPUT_SCREEN.name) {
                InputScreen(
                    viewModel = viewModel,
                    goToCardsScreen = goToCards
                )
            }

            composable(ScreenRoute.CARDS_SCREEN.name) {
                CityCardsScreen(
                    viewModel = viewModel,
                    goToWeatherScreen = goToWeather,
                    goToInputScreen = goToInput
                )
            }

            composable(ScreenRoute.WEATHER_SCREEN.name) {
                WeatherScreen(
                    viewModel = viewModel,
                    goToCardsScreen = goToCards
                )
            }
        }
    }
}