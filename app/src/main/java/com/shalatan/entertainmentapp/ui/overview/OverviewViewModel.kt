package com.shalatan.entertainmentapp.ui.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.network.TmdbApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response status
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the status String
    val status: LiveData<String>
        get() = _status

    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: LiveData<List<Movie>>
        get() = _nowPlayingMovies

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

    private val _navigateToSelectedMovieListGrid = MutableLiveData<List<Movie>>()
    val navigateToSelectedMovieListGrid: LiveData<List<Movie>>
        get() = _navigateToSelectedMovieListGrid

    private val _showMovieListGrid = MutableLiveData<List<Movie>>()
    val showMovieListGrid: LiveData<List<Movie>>
        get() = _showMovieListGrid

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var greetingText: String = getGreetingMessage()

    init {
        fetchMoviesLists()
    }

    private fun getGreetingMessage(): String {
        val c = Calendar.getInstance()
        return when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Good Morning"
            in 12..15 -> "Good Afternoon"
            in 16..20 -> "Good Evening"
            in 21..23 -> "Good Night"
            else -> "Hello"
        }
    }

    private fun fetchMoviesLists() {
        coroutineScope.launch {
            val getNowPlayingMoviesDeferred = TmdbApi.RETROFIT_SERVICE.getNowPlayingMoviesAsync()
            val getPopularMoviesDeferred = TmdbApi.RETROFIT_SERVICE.getPopularMoviesAsync()
            val getTopRatedMoviesDeferred = TmdbApi.RETROFIT_SERVICE.getTopRatedMoviesAsync()
            val getUpcomingMoviesDeferred = TmdbApi.RETROFIT_SERVICE.getUpcomingMoviesAsync()
            try {
                _nowPlayingMovies.value = getNowPlayingMoviesDeferred.await().movies
                _popularMovies.value = getPopularMoviesDeferred.await().movies
                _topRatedMovies.value = getTopRatedMoviesDeferred.await().movies
                _upcomingMovies.value = getUpcomingMoviesDeferred.await().movies
            } catch (t: Throwable) {
                _status.value = "Failure" + t.message
                Log.e("ERROR", _status.value.toString())

            }
        }
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedMovie] [MutableLiveData]
     * @param movie The [Movie] that was clicked on.
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