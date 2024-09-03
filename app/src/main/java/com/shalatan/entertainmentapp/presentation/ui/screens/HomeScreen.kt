package com.shalatan.entertainmentapp.presentation.ui.screens

import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shalatan.entertainmentapp.presentation.contracts.HomeContract
import com.shalatan.entertainmentapp.presentation.ui.components.MovieListHorizontal
import com.shalatan.entertainmentapp.presentation.ui.components.MovieSlot
import com.shalatan.entertainmentapp.presentation.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val uiState by homeViewModel.uiState.collectAsState()
    Log.e("EApp", "HomeScreen: $uiState")
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "Movies Hex") },
            scrollBehavior = scrollBehavior
        )
    }) {
        HomeScreenContent(modifier = Modifier.padding(it), uiState) { movieId ->
            //clicked movie item
            homeViewModel.setEvent(HomeContract.HomeEvent.OnMovieItemClick(movieId))
            navController.invoke()
        }
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    uiState: HomeContract.MovieComponentUiState,
    movieItemClicked: (Int) -> Unit
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        MovieSlot(
            modifier = Modifier,
            title = "Now Playing Movies"
        ) {
            MovieListHorizontal(moviesList = uiState.homeScreenData.nowPlayingMovies) {
                movieItemClicked.invoke(it)
            }
        }
        MovieSlot(
            modifier = Modifier,
            title = "Popular Movies"
        ) {
            MovieListHorizontal(moviesList = uiState.homeScreenData.popularMovies) {
                movieItemClicked.invoke(it)
            }
        }
        MovieSlot(
            modifier = Modifier,
            title = "Top Rated Movies"
        ) {
            MovieListHorizontal(moviesList = uiState.homeScreenData.topRatedMovies) {
                movieItemClicked.invoke(it)
            }
        }
        MovieSlot(
            modifier = Modifier,
            title = "Upcoming Movies"
        ) {
            MovieListHorizontal(moviesList = uiState.homeScreenData.upcomingMovies) {
                movieItemClicked.invoke(it)

            }
        }
    }
}
