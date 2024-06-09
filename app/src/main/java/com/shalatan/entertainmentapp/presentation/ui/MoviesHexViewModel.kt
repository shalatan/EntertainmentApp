package com.shalatan.entertainmentapp.presentation.ui

import androidx.lifecycle.viewModelScope
import com.app.base.BaseViewModel
import com.app.base.StateFullResult
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.network.ApiHelperImpl
import com.shalatan.entertainmentapp.presentation.contracts.MainContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val TAG = "app_log"

@HiltViewModel
class NewMainViewModel @Inject constructor(
    private val apiHelperImpl: ApiHelperImpl,
) : BaseViewModel<Event, MainUIState, UIEffect>() {

    override fun createInitialState(): MainUIState {
        return MainUIState(
            homeScreenData = HomeScreenData(),
            movieDetailScreenData = MovieDetailScreenData()
        )
    }

    init {
        viewModelScope.launch {
            fetchMovies()
        }
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnMovieItemClick -> {
                fetchMovieDetail(event.movieId)
                setEffect { UIEffect.NavigateToMovieDetailScreen }
            }
        }
    }

    private suspend fun fetchMovies() {
        val upcomingMoviesResult = apiHelperImpl.getUpcomingMovies()
        val topRatedMoviesResult = apiHelperImpl.getTopRatedMovies()
        val popularMoviesResult = apiHelperImpl.getPopularMovies()
        val nowPlayingMoviesResult = apiHelperImpl.getNowPlayingMovies()
        handleMovieResult(upcomingMoviesResult) { data ->
            setState {
                copy(homeScreenData = homeScreenData.copy(upComingMovies = data.movies))
            }
        }
        handleMovieResult(topRatedMoviesResult) { data ->
            setState {
                copy(homeScreenData = homeScreenData.copy(topMovies = data.movies))
            }
        }
        handleMovieResult(popularMoviesResult) { data ->
            setState {
                copy(homeScreenData = homeScreenData.copy(popularMovies = data.movies))
            }
        }
        handleMovieResult(nowPlayingMoviesResult) { data ->
            setState {
                copy(homeScreenData = homeScreenData.copy(trendingMovies = data.movies))
            }
        }
    }

    private fun handleMovieResult(
        result: StateFullResult<MovieResponse>,
        onSuccess: (MovieResponse) -> Unit
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

    private fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            when (val movieDetail = apiHelperImpl.getMovieDetail(movieId)) {
                is StateFullResult.Failure -> Unit
                is StateFullResult.Loading -> Unit
                is StateFullResult.Success -> {
                    setState {
                        copy(movieDetailScreenData = movieDetailScreenData.copy(completeMovieDetail = movieDetail.data))
                    }
                }
            }
            Timber.d("HAHA: stateDataFetchMovieDetail: ${currentState}")
        }
    }
}