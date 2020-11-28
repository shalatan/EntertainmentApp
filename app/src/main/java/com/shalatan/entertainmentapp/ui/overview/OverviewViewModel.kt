package com.shalatan.entertainmentapp.ui.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.network.LmdbApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {


    // The internal MutableLiveData String that stores the most recent response status
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the status String
    val status: LiveData<String>
        get() = _status

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>>
        get() = _topRatedMovies

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>>
        get() = _upcomingMovies

    private val _navigateToSelectedMovie = MutableLiveData<Movie>()
    val navigateToSelectedMovie: LiveData<Movie>
        get() = _navigateToSelectedMovie

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Log.d("OverviewViewModel : ", "Created")
        fetchMoviesLists()
    }

    private fun fetchMoviesLists() {
        coroutineScope.launch {
            val getPopularMoviesDeferred = LmdbApi.retrofitService.getPopularMovies()
            val getTopRatedMoviesDeferred = LmdbApi.retrofitService.getTopRatedMovies()
            val getUpcomingMoviesDeferred = LmdbApi.retrofitService.getUpcomingMovies()
            try {
                val popularMoviesList = getPopularMoviesDeferred.await()
                _popularMovies.value = popularMoviesList.movies

                val topRatedMoviesList = getTopRatedMoviesDeferred.await()
                _topRatedMovies.value = topRatedMoviesList.movies

                val upcomingMoviesList = getUpcomingMoviesDeferred.await()
                _upcomingMovies.value = upcomingMoviesList.movies

                _status.value = "Fetched " + _popularMovies.value!!.size + " result"

            } catch (t: Throwable) {
                _status.value = "Failure" + t.message

            }
        }
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param marsProperty The [MarsProperty] that was clicked on.
     */
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}