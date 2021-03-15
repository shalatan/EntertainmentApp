package com.shalatan.entertainmentapp.ui.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.network.TmdbApi
import com.shalatan.entertainmentapp.utils.Constants
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

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        fetchMoviesLists()
    }

    private fun fetchMoviesLists() {
        fetchNowPlayingMovies()
        fetchTopRatedMovies()
        fetchPopularMovies()
        fetchUpcomingMovies()
    }

    private fun fetchNowPlayingMovies(){
        coroutineScope.launch {
            val getNowPlayingMoviesDeferred = TmdbApi.RETROFIT_SERVICE.getNowPlayingMoviesAsync()
            _nowPlayingMovies.value = getNowPlayingMoviesDeferred.await().movies
        }
    }

    private fun fetchPopularMovies(){
        coroutineScope.launch {
            val getPopularMoviesDeferred = TmdbApi.RETROFIT_SERVICE.getPopularMoviesAsync()
            _popularMovies.value = getPopularMoviesDeferred.await().movies
        }
    }

    private fun fetchTopRatedMovies(){
        coroutineScope.launch {
            val getTopRatedMoviesDeferred = TmdbApi.RETROFIT_SERVICE.getTopRatedMoviesAsync()
            _topRatedMovies.value = getTopRatedMoviesDeferred.await().movies
        }
    }

    private fun fetchUpcomingMovies(){
        coroutineScope.launch {
            val getUpcomingMoviesDeferred = TmdbApi.RETROFIT_SERVICE.getUpcomingMoviesAsync()
            _upcomingMovies.value = getUpcomingMoviesDeferred.await().movies
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
     * After the navigation has taken place, make sure navigateToSelectedMovie is set to null
     */
    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}