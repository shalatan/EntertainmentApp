package com.shalatan.entertainmentapp.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(private val repository: NetworkRepository) :
    ViewModel() {

    private val _status = MutableLiveData<String>()
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

    private val _navigateToSelectedMovie = MutableLiveData<Movie?>()
    val navigateToSelectedMovie: LiveData<Movie?>
        get() = _navigateToSelectedMovie

    init {
        fetchMoviesData()
    }

    private fun fetchMoviesData() {
        viewModelScope.launch {
            val topMovies = repository.getTopRatedMoviesAsync()
            val popularMovies = repository.getPopularMoviesAsync()
            val nowPlayingMovies = repository.getNowPlayingMoviesAsync()
            val upcomingMovies = repository.getUpcomingMoviesAsync()
            try {
                _topRatedMovies.value = topMovies.await().movies
                _popularMovies.value = popularMovies.await().movies
                _nowPlayingMovies.value = nowPlayingMovies.await().movies
                _upcomingMovies.value = upcomingMovies.await().movies
            } catch (t: Throwable) {
                _status.value = "Failure" + t.message
            }
        }
    }

    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }
}