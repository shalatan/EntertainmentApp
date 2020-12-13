package com.shalatan.entertainmentapp.ui.moviesection

import android.app.Application
import androidx.lifecycle.*
import com.shalatan.entertainmentapp.database.MovieDAO
import com.shalatan.entertainmentapp.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedContentViewModel(val database: MovieDAO) :
    ViewModel() {

    val watchedMovies = database.getAllWatchedMovies()
    val watchLaterMovies = database.getAllWatchLaterMovies()


    private val _navigateToSelectedMovie = MutableLiveData<Movie>()
    val navigateToSelectedMovie: LiveData<Movie>
        get() = _navigateToSelectedMovie

    /**
     * to navigate to clicked movie detail fragment
     */
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsComplete() {
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
        withContext(Dispatchers.IO) {
            watchLaterMovies.value?.get(position)?.let {
                database.delete(it)
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
        withContext(Dispatchers.IO) {
            watchedMovies.value?.get(position)?.let {
                database.delete(it)
            }
        }
    }
}