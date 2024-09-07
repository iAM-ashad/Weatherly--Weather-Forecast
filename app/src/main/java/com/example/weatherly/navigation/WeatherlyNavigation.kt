package com.example.weatherly.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherly.screen.main.MainScreen
import com.example.weatherly.screen.main.MainViewModel
import com.example.weatherly.screen.splash.SplashScreen

@Composable
fun WeatherlyNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherlyScreens.SplashScreen.name
    ) {
        composable(WeatherlyScreens.SplashScreen.name) {
            SplashScreen(navController)
        }
        composable(WeatherlyScreens.MainScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, mainViewModel)
        }
    }
}