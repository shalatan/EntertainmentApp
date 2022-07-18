package com.shalatan.entertainmentapp.ui.moviesection

import androidx.lifecycle.*
import com.shalatan.entertainmentapp.MyApplication
import com.shalatan.entertainmentapp.database.DatabaseRepository
import com.shalatan.entertainmentapp.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SavedContentViewModel @Inject constructor(private val repository: DatabaseRepository) :
    ViewModel() {

    val watchedMovies = repository.getAllWatchedMovies()
    val watchLaterMovies = repository.getAllWatchLaterMovies()
    val recommendationMovies = repository.getAllRecommendedMovies()

    private val _navigateToSelectedMovie = MutableLiveData<Movie?>()
    val navigateToSelectedMovie: LiveData<Movie?>
        get() = _navigateToSelectedMovie

    fun updateHighestRecommendationWeight() {
        var highest = 0
        viewModelScope.launch {
            highest = repository.getHighestRecommendationWeight()
            MyApplication.highest = highest
            Timber.tag("ABCD").d("VM Highest = $highest")
            Timber.tag("ABCD").d("MA Highest = ${MyApplication.highest}")
        }
    }

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
    fun deleteWatchLaterMovie(position: Int) {
        viewModelScope.launch {
            watchLaterMovies.value?.get(position)?.let {
                repository.changeMovieWatchLaterStatus(it.Id, isWatchLater = false)
            }
        }
    }

    /**
     * Remove movie from watched movies list
     */
    fun deleteWatchedMovie(position: Int) {
        viewModelScope.launch {
            watchedMovies.value?.get(position)?.let {
                repository.changeMovieRatedStatus(it.Id, isRated = false, 0f)
            }
        }
    }
}