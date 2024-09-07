package com.example.weatherly.widgets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherlyTopBar (
    title: String,
    elevation: Dp,
    icon: ImageVector? = null,
    isMainScreen: Boolean,
    navController: NavController,
    onAddActionButtonClicked: () -> Unit,
    onButtonClicked: () -> Unit
) {
    TopAppBar(
        title = {
        Text(
            text = title,
            color = Color.White,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue
        ),
        modifier = Modifier
            .fillMaxWidth()

    )
}