package ru.android.anyweather.composables.appParts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarContent(
    modifier: Modifier = Modifier
){
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "APP_NAME_RESOURCE" //TODO clear hardcode
            )
        }
    )
}