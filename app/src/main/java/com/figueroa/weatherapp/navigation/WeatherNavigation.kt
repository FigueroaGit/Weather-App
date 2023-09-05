package com.figueroa.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.figueroa.weatherapp.screens.about.AboutScreen
import com.figueroa.weatherapp.screens.favorites.FavoritesScreen
import com.figueroa.weatherapp.screens.main.MainScreen
import com.figueroa.weatherapp.screens.main.MainViewModel
import com.figueroa.weatherapp.screens.search.SearchScreen
import com.figueroa.weatherapp.screens.settings.SettingsScreen
import com.figueroa.weatherapp.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(route = WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable(
            route = "$route/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                },
            ),
        ) { navBack ->
            navBack.arguments?.getString("city").let { city ->

                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel, city = city)
            }
        }

        composable(route = WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(route = WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(route = WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }

        composable(route = WeatherScreens.FavoriteScreen.name) {
            FavoritesScreen(navController = navController)
        }
    }
}
