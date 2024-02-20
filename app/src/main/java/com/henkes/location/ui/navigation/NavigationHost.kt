package com.henkes.location.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.henkes.location.ui.screen.home.HomeScreen
import com.henkes.location.ui.screen.locations.LocationsScreen
import com.henkes.location.ui.screen.splash.SplashScreen

@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        route = "graph",
        startDestination = Route.Splash.path
    ) {
        composable(Route.Splash.path) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Route.Home.path)
                }
            )
        }
        composable(Route.Home.path) {
            HomeScreen(
                onNavigateToLocations = {
                    navController.navigate(Route.Locations.path)
                }
            )
        }
        composable(Route.Locations.path) {
            LocationsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
