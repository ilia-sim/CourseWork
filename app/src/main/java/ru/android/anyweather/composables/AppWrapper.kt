package ru.android.anyweather.composables

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.android.anyweather.composables.appParts.MainContent
import ru.android.anyweather.composables.appParts.TopBarContent

@Composable
fun AppWrapper(){
    Scaffold(
        topBar = {
            TopBarContent()
        }
    ) {
        innerPadding ->
        MainContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
        )
    }
}