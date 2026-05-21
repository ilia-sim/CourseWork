package ru.android.anyweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.android.anyweather.composables.AppWrapper
import ru.android.anyweather.ui.theme.AnyWeatherTheme

const val METEO_BASE_URL = "https://api.open-meteo.com"
const val NINJA_BASE_URL = "https://api.api-ninjas.com"

val RETROFIT_METEO: Retrofit = Retrofit.Builder()
    .baseUrl(METEO_BASE_URL)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    //.addConverterFactory(ScalarsConverterFactory.create())
    .build()

val RETROFIT_NINJA: Retrofit = Retrofit.Builder()
    .baseUrl(NINJA_BASE_URL)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    //.addConverterFactory(ScalarsConverterFactory.create())
    .build()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AnyWeatherTheme {
                AppWrapper()
            }
        }
    }
}