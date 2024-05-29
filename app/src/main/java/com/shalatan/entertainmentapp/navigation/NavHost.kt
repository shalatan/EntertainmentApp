package com.shalatan.entertainmentapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shalatan.entertainmentapp.presentation.ui.screens.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        composable(route = Screen.HomeScreen.route) { backStackEntry ->
            HomeScreen()
        }
    }
}