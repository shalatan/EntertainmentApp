package com.shalatan.entertainmentapp.ui.moviesection

import androidx.lifecycle.*
import com.shalatan.entertainmentapp.model.Movie
import kotlinx.coroutines.launch

class SavedContentViewModel(private val repository: SavedContentRepository) :
    ViewModel() {

    val watchedMovies = repository.getAllWatchedMovies()
    val watchLaterMovies = repository.getAllWatchLaterMovies()

    private val _navigateToSelectedMovie = MutableLiveData<Movie?>()
    val navigateToSelectedMovie: LiveData<Movie?>
        get() = _navigateToSelectedMovie

    /**
     * to navigate to clicked movie detail fragment
     */
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsCompleted() {
        _navigateToSelectedMovie.value = null
    }

    /**
     * Remove movie from watch later movies list
     */
    fun removeFromWatchLater(position: Int) {
        viewModelScope.launch {
            deleteWatchLaterMovie(position)
        }
    }

    private suspend fun deleteWatchLaterMovie(position: Int) {
        viewModelScope.launch {
            watchLaterMovies.value?.get(position)?.let {
                repository.deleteMovieFromDatabase(it)
            }
        }
    }

    /**
     * Remove movie from watched movies list
     */
    fun removeFromWatched(position: Int) {
        viewModelScope.launch {
            deleteWatchedMovie(position)
        }
    }

    private suspend fun deleteWatchedMovie(position: Int) {
        viewModelScope.launch {
            watchedMovies.value?.get(position)?.let {
                repository.deleteMovieFromDatabase(it)
            }
        }
    }
}