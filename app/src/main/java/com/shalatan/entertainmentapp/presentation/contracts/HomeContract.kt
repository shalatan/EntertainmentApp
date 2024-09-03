package com.shalatan.entertainmentapp.presentation.contracts

import com.app.base.UiEffect
import com.app.base.UiEvent
import com.app.base.UiState
import com.shalatan.entertainmentapp.domain.model.Movie

class HomeContract {

    sealed class HomeEvent : UiEvent {
        data class OnMovieItemClick(val movieId: Int) : HomeEvent()
    }

    sealed class ChatUIState : UiState {
        data object Idle : ChatUIState()
        data object Loading : ChatUIState()
        data class Error(val message: String) : ChatUIState()
        data object Success : ChatUIState()
    }

    data class MovieComponentUiState(
        val homeScreenData: HomeScreenData,
        val selectedMovieDetailScreenData: SelectedMovieDetailScreenData
    ) : ChatUIState()

    data class HomeScreenData(
        val upcomingMovies: List<Movie>,
        val popularMovies: List<Movie>,
        val nowPlayingMovies: List<Movie>,
        val topRatedMovies: List<Movie>,
    ) : ChatUIState()

    data class SelectedMovieDetailScreenData(
        val movie: Movie,
        val similarMovies: List<Movie>,
    ) : ChatUIState()

    sealed class HomeEffect : UiEffect {

    }
}