package com.shalatan.entertainmentapp.navigation

enum class Screen {
    HOME_SCREEN,
    MOVIE_DETAILS_SCREEN
}

sealed class NavigationItem(val route: String) {
    data object HomeScreen : NavigationItem(route = Screen.HOME_SCREEN.name)
    data object MovieDetailsScreen : NavigationItem(route = Screen.MOVIE_DETAILS_SCREEN.name)
}