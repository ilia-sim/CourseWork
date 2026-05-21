package ru.android.anyweather.composables.appParts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.android.anyweather.ScreenRoute
import ru.android.anyweather.composables.screens.CitiesListScreenContent
import ru.android.anyweather.composables.screens.CityScreenContent
import ru.android.anyweather.composables.screens.InputScreenContent
import ru.android.anyweather.viewModels.MainContentViewModel

@Composable
fun MainContent(
    modifier : Modifier = Modifier,
    viewModel : MainContentViewModel = MainContentViewModel()
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.INPUT_SCREEN.name,
        modifier = modifier,
    ) {
        composable(ScreenRoute.INPUT_SCREEN.name) {
            InputScreenContent(
                viewModel = viewModel,
                onCheckWeather = {
                    navController.navigate(ScreenRoute.CITIES_LIST_SCREEN.name)
                }
            )
        }

        composable(ScreenRoute.CITIES_LIST_SCREEN.name) {
            CitiesListScreenContent(
                viewModel = viewModel,
                onCityCardClick = {
                    navController.navigate(ScreenRoute.CITY_SCREEN.name)
                },
                onBackClick ={
                    navController.navigate(ScreenRoute.INPUT_SCREEN.name)
                }
            )
        }

        composable(ScreenRoute.CITY_SCREEN.name) {
            CityScreenContent(
                viewModel = viewModel,
                onBackClick ={
                    navController.navigate(ScreenRoute.INPUT_SCREEN.name)
                }
            )
        }
    }
}