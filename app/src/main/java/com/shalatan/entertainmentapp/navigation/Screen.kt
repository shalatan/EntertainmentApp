package com.shalatan.entertainmentapp.navigation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(route = "HomeScreen")
}