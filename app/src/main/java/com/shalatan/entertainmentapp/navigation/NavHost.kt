package com.shalatan.entertainmentapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shalatan.entertainmentapp.presentation.ui.screens.HomeScreen
import com.shalatan.entertainmentapp.presentation.ui.screens.MovieScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationItem.Home.route) {
        composable(route = NavigationItem.Home.route) {
            HomeScreen {
                navController.navigate(NavigationItem.MovieDetailScreen.route)
            }
        }

        composable(route = NavigationItem.MovieDetailScreen.route) { backStackEntry ->
//            val movieId = backStackEntry.arguments?.getString("movieId")
            MovieScreen(navController = navController)
        }
    }
}