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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.shalatan.entertainmentapp.presentation.ui.NewMainViewModel
import com.shalatan.entertainmentapp.presentation.ui.theme.Dimensions
import com.shalatan.entertainmentapp.ui.ui.theme.EntertainmentAppTheme

@Preview(
    showSystemUi = true
)
@Composable
private fun HomeScreenPreview() {
    EntertainmentAppTheme {
        HomeScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: NewMainViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val uiState by viewModel.uiState.collectAsState()
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
                MovieListHorizontal(moviesList = uiState.homeScreenData.upComingMovies)
            }
            MovieSlot(
                modifier = Modifier.padding(top = Dimensions.dimen_16),
                title = "Popular Movies"
            ) {
                MovieListHorizontal(moviesList = uiState.homeScreenData.upComingMovies)
            }
            MovieSlot(
                modifier = Modifier.padding(top = Dimensions.dimen_16),
                title = "Top Rated Movies"
            ) {
                MovieListHorizontal(moviesList = uiState.homeScreenData.upComingMovies)
            }
            MovieSlot(
                modifier = Modifier.padding(top = Dimensions.dimen_16),
                title = "Trending Movies"
            ) {
                MovieListHorizontal(moviesList = uiState.homeScreenData.upComingMovies)
            }
        }
    }
}

