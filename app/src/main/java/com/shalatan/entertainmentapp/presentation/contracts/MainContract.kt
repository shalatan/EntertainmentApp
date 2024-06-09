package com.shalatan.entertainmentapp.presentation.contracts

import com.app.base.UiEffect
import com.app.base.UiEvent
import com.app.base.UiState
import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.model.Movie

class MainContract {

    sealed class Event : UiEvent {
        data class OnMovieItemClick(val movieId: Int) : Event()
    }

    sealed interface UIState : UiState {
        data object Idle : UIState
        data object Loading : UIState
        data class Error(val message: String) : UIState
        data object Success : UIState
    }

    data class MainUIState(
        val homeScreenData: HomeScreenData,
        val movieDetailScreenData: MovieDetailScreenData,
    ) : UIState


    data class MovieDetailScreenData(
        val completeMovieDetail: CompleteMovieDetail? = null,
        val similarMovies: List<Movie>? = null,
    )

    data class HomeScreenData(
        val trendingMovies: List<Movie>? = null,
        val popularMovies: List<Movie>? = null,
        val upComingMovies: List<Movie>? = null,
        val topMovies: List<Movie>? = null,
    )

    sealed class UIEffect : UiEffect {
        data object NavigateToMovieDetailScreen : UIEffect()
    }
}
