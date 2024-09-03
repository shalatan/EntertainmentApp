package com.shalatan.entertainmentapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.app.base.BaseViewModel
import com.app.base.StateFullResult
import com.shalatan.entertainmentapp.data.remote.ApiHelperImpl
import com.shalatan.entertainmentapp.domain.model.Movie
import com.shalatan.entertainmentapp.domain.model.MovieResponse
import com.shalatan.entertainmentapp.presentation.contracts.HomeContract.*
import com.shalatan.entertainmentapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiHelperImpl: ApiHelperImpl
) : BaseViewModel<HomeEvent, MovieComponentUiState, HomeEffect>() {

    init {
        viewModelScope.launch {
            loadHomeScreen()
        }
    }

    private suspend fun loadHomeScreen() {
        val upcomingMoviesResult = apiHelperImpl.getUpcomingMovies()
        val topRatedMoviesResult = apiHelperImpl.getTopRatedMovies()
        val popularMoviesResult = apiHelperImpl.getPopularMovies()
        val nowPlayingMoviesResult = apiHelperImpl.getNowPlayingMovies()
        handleMovieResult(upcomingMoviesResult) { data ->
            setState {
                copy(homeScreenData = homeScreenData.copy(upcomingMovies = data.movies))
            }
        }
        handleMovieResult(topRatedMoviesResult) { data ->
            setState {
                copy(homeScreenData = homeScreenData.copy(topRatedMovies = data.movies))
            }
        }
        handleMovieResult(popularMoviesResult) { data ->
            setState {
                copy(homeScreenData = homeScreenData.copy(popularMovies = data.movies))
            }
        }
        handleMovieResult(nowPlayingMoviesResult) { data ->
            setState {
                copy(homeScreenData = homeScreenData.copy(nowPlayingMovies = data.movies))
            }
        }
    }

    private fun handleMovieResult(
        result: StateFullResult<MovieResponse>, onSuccess: (MovieResponse) -> Unit
    ) {
        when (result) {
            is StateFullResult.Failure -> {
//                setState { copy(UIState.Error(result.message)) }
//                sendEffect { UIEffect.ShowToast(result.message) }
            }

            is StateFullResult.Loading -> {
                // Optionally handle loading state for individual requests
            }

            is StateFullResult.Success -> {
                onSuccess(result.data)
            }
        }
    }

    override fun createInitialState(): MovieComponentUiState {
        return MovieComponentUiState(
            homeScreenData = HomeScreenData(
                upcomingMovies = mutableListOf(),
                topRatedMovies = mutableListOf(),
                popularMovies = mutableListOf(),
                nowPlayingMovies = mutableListOf()
            ), selectedMovieDetailScreenData = SelectedMovieDetailScreenData(
                movie = Movie(), similarMovies = mutableListOf()
            )
        )
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnMovieItemClick -> {
                fetchMovieData(movieId = event.movieId)
            }

            else -> {}
        }
    }

    private fun fetchMovieData(movieId: Int) {
        viewModelScope.launch {
            when (val movieDetailResult = apiHelperImpl.getMovieData(movieId)) {
                is StateFullResult.Failure -> TODO()
                is StateFullResult.Loading -> TODO()
                is StateFullResult.Success -> {
                    setState {
                        copy(
                            selectedMovieDetailScreenData = selectedMovieDetailScreenData.copy(
                                movie = movieDetailResult.data
                            )
                        )
                    }
                    Log.e(Constants.TAG, "fetchMovieData: ${movieDetailResult.data}")
                }
            }
        }
    }
}