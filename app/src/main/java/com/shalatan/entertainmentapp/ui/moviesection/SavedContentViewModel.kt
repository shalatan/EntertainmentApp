package com.shalatan.entertainmentapp.ui.moviesection

import androidx.lifecycle.*
import com.shalatan.entertainmentapp.MyApplication
import com.shalatan.entertainmentapp.database.DatabaseRepository
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SavedContentViewModel @Inject constructor(private val repository: DatabaseRepository) :
    ViewModel() {

    private val LOG = MyApplication.LOG

    private val _savedMoviesFlow = MutableStateFlow<List<SavedMovie>>(emptyList())
    val savedMoviesFlow: StateFlow<List<SavedMovie>> = _savedMoviesFlow

    private val _navigateToSelectedMovie = MutableLiveData<Movie?>()
    val navigateToSelectedMovie: LiveData<Movie?>
        get() = _navigateToSelectedMovie

    /**
     * update the global variable 'highest', so recommendation percentage can be calculated
     * inside bindingAdapter.kt
     */
    fun updateHighestRecommendationWeight() {
        val highest = _savedMoviesFlow.value[0].recommendationWeight
        Timber.d("$LOG currentHighestRecWeight: $highest")
        MyApplication.highest = highest
    }

    fun fetchWatchLaterMovies(
        fromWatchLater: Boolean = false,
        fromWatched: Boolean = false,
        fromRecommendation: Boolean = false
    ) {
        val a = when (true) {
            fromWatchLater -> repository.getAllWatchLaterMovies()
            fromWatched -> repository.getAllRatedMovies()
            fromRecommendation -> repository.getAllRecommendedMovies()
            else -> {
                repository.getAllRatedMovies()
            }
        }
        viewModelScope.launch {
            a.flowOn(Dispatchers.IO).catch {
                Timber.d("$LOG exception: $it")
            }.collect {
                _savedMoviesFlow.value = it
                Timber.d("$LOG ratedMovies: ${it.size}")
            }
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
     * delete movie from saved directory
     */
    fun removeMovie(position: Int, fromWatchLater: Boolean = false, fromWatched: Boolean = false) {
        viewModelScope.launch {
            if (fromWatched) {
                savedMoviesFlow.value[position].let {
                    repository.changeMovieRatedStatus(it.Id, isRated = false, 0f)
                }
            } else if (fromWatchLater) {
                savedMoviesFlow.value[position].let {
                    repository.changeMovieWatchLaterStatus(it.Id, isWatchLater = false)
                }
            }
        }
    }
}