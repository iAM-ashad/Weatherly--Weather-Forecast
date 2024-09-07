package com.example.weatherly.screen.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherly.data.DataOrException
import com.example.weatherly.model.Weather
import com.example.weatherly.widgets.WeatherlyTopBar

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = "Shillong")
    }.value

    if (weatherData.loading == true) {
        Log.d("Loading", "LOADING!!!")
        CircularProgressIndicator()
    }else if (weatherData.data != null) {
        Log.d("Loaded", "LOADED")
        MainScaffold(weather = weatherData.data!!, navController)
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(
        topBar = {
            WeatherlyTopBar(
                title = "Weatherly",
                elevation = 10.dp,
                icon = null,
                isMainScreen = true,
                navController = navController,
                onAddActionButtonClicked = { /*TODO*/ },
                onButtonClicked = {}
            )
        }
    ) { innerPadding->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            MainContent(
                data = weather
            )
        }
    }
}

@Composable
fun MainContent(
    data: Weather,
    modifier: Modifier = Modifier
) {
    Text(
        text = data.city.name,
        color = Color.Black
    )
}