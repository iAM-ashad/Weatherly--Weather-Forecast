package com.example.weatherly.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherly.screen.about.AboutScreen
import com.example.weatherly.screen.favorites.FavoritesScreen
import com.example.weatherly.screen.main.MainScreen
import com.example.weatherly.screen.main.MainViewModel
import com.example.weatherly.screen.search.SearchScreen
import com.example.weatherly.screen.setting.SettingsScreen
import com.example.weatherly.screen.splash.SplashScreen

@Composable
fun WeatherlyNavigation(
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherlyScreens.SplashScreen.name
    ) {
        composable(WeatherlyScreens.SplashScreen.name) {
            SplashScreen(navController)
        }
        val route = WeatherlyScreens.MainScreen.name
        composable(
            "$route/{city}/{dataMode}",
            arguments = listOf(
                navArgument("city") {type = NavType.StringType},
                navArgument("dataMode") {type = NavType.StringType}
            )
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city")
            val dataMode = backStackEntry.arguments?.getString("dataMode")
            val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    city = city,
                    dataMode = dataMode.toString()
                )
            }
        val sRoute = WeatherlyScreens.SearchScreen.name
        composable(
            "$sRoute/{dataMode}",
            arguments = listOf(
                navArgument("dataMode") {type = NavType.StringType}
            )
        ) { navBack->
            navBack.arguments?.getString("dataMode").let { dataMode->
                SearchScreen(navController = navController, dataMode = dataMode.toString())
            }
        }
        composable(WeatherlyScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }
        val fRoute = WeatherlyScreens.FavoritesScreen.name
        composable(
            "$fRoute/{dataMode}",
            arguments = listOf(
                navArgument("dataMode") {type = NavType.StringType}
            )
        ) { navBack->
            navBack.arguments?.getString("dataMode").let { dataMode->
                FavoritesScreen(navController = navController, dataMode = dataMode.toString())
            }
        }
        val rSearch = WeatherlyScreens.SettingsScreen.name
        composable(
            "$rSearch/{city}",
            arguments = listOf(
                navArgument("city"){type = NavType.StringType}
            )
        ) { navBack ->
            navBack.arguments?.getString("city").let { city->
                SettingsScreen(navController = navController, city = city)
            }
        }
    }
}