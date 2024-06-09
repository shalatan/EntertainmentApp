package com.shalatan.entertainmentapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shalatan.entertainmentapp.presentation.ui.NewMainViewModel
import com.shalatan.entertainmentapp.presentation.ui.screens.HomeScreen
import com.shalatan.entertainmentapp.presentation.ui.screens.MovieDetailScreen
import com.shalatan.entertainmentapp.presentation.ui.screens.MovieDetailScreenContent

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: NewMainViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = NavigationItem.HomeScreen.route
    ) {

        composable(route = NavigationItem.HomeScreen.route) { backStackEntry ->
            HomeScreen(navController = navController)
        }

        composable(route = NavigationItem.MovieDetailsScreen.route) { backStackEntry ->
            MovieDetailScreen(viewModel = viewModel)
        }
    }
}