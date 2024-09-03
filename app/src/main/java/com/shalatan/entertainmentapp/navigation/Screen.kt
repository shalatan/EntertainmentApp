package com.shalatan.entertainmentapp.navigation

enum class Screen {
    HOME,
    MOVIE_DETAIL
}

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(route = Screen.HOME.name)
    data object MovieDetailScreen : NavigationItem(route = Screen.MOVIE_DETAIL.name)
}