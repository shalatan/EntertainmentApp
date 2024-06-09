package com.shalatan.entertainmentapp.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shalatan.entertainmentapp.navigation.NavigationItem
import com.shalatan.entertainmentapp.navigation.Screen
import com.shalatan.entertainmentapp.presentation.contracts.MainContract
import com.shalatan.entertainmentapp.presentation.ui.NewMainViewModel
import com.shalatan.entertainmentapp.presentation.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: NewMainViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MainContract.UIEffect.NavigateToMovieDetailScreen -> {
                    navController.navigate(NavigationItem.MovieDetailsScreen.route)
                }
            }
        }
    }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "Movies Hex") }, scrollBehavior = scrollBehavior
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            MovieSlot(
                modifier = Modifier,
                title = "Upcoming Movies"
            ) {
                MovieListHorizontal(
                    moviesList = uiState.homeScreenData.upComingMovies,
                    onItemClick = {
                        viewModel.setEvent(MainContract.Event.OnMovieItemClick(movieId = it))
                    })
            }
            MovieSlot(
                modifier = Modifier.padding(top = Dimensions.dimen_16),
                title = "Popular Movies"
            ) {
                MovieListHorizontal(
                    moviesList = uiState.homeScreenData.popularMovies,
                    onItemClick = {
                        viewModel.setEvent(MainContract.Event.OnMovieItemClick(movieId = it))
                    })
            }
            MovieSlot(
                modifier = Modifier.padding(top = Dimensions.dimen_16),
                title = "Top Rated Movies"
            ) {
                MovieListHorizontal(
                    moviesList = uiState.homeScreenData.topMovies,
                    onItemClick = {
                        viewModel.setEvent(MainContract.Event.OnMovieItemClick(movieId = it))

                    })
            }
            MovieSlot(
                modifier = Modifier.padding(top = Dimensions.dimen_16),
                title = "Trending Movies"
            ) {
                MovieListHorizontal(
                    moviesList = uiState.homeScreenData.trendingMovies,
                    onItemClick = {
                        viewModel.setEvent(MainContract.Event.OnMovieItemClick(movieId = it))
                    })
            }
        }
    }
}

