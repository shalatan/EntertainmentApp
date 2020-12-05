package com.shalatan.entertainmentapp.ui.moviesection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shalatan.entertainmentapp.database.MovieDAO
import com.shalatan.entertainmentapp.model.Movie

class SavedContentViewModel(val database: MovieDAO, application: Application) :
    AndroidViewModel(application) {

    val watchedMovies = database.getAllWatchedMovies()
    val watchLaterMovies = database.getAllWatchLaterMovies()

    private val _navigateToSelectedMovie = MutableLiveData<Movie>()
    val navigateToSelectedMovie: LiveData<Movie>
        get() = _navigateToSelectedMovie

    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }
}