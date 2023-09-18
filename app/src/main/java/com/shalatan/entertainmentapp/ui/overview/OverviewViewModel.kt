package com.shalatan.entertainmentapp.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalatan.entertainmentapp.MyApplication
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.network.Response
import com.shalatan.entertainmentapp.network.NetworkRepository
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
class OverviewViewModel @Inject constructor(private val repository: NetworkRepository) :
    ViewModel() {

    private val LOG = MyApplication.LOG

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _nowPlayingMoviesFlow =
        MutableStateFlow<Response<List<Movie>>>(value = Response.Loading)
    val nowPlayingMoviesFlow: StateFlow<Response<List<Movie>>> = _nowPlayingMoviesFlow

    private val _popularMoviesFlow =
        MutableStateFlow<Response<List<Movie>>>(value = Response.Loading)
    val popularMoviesFlow: StateFlow<Response<List<Movie>>> = _popularMoviesFlow

    private val _topRatedMoviesFlow =
        MutableStateFlow<Response<List<Movie>>>(value = Response.Loading)
    val topRatedMoviesFlow: StateFlow<Response<List<Movie>>> = _topRatedMoviesFlow

    private val _upcomingMoviesFlow =
        MutableStateFlow<Response<List<Movie>>>(value = Response.Loading)
    val upcomingMoviesFlow: StateFlow<Response<List<Movie>>> = _upcomingMoviesFlow

    private val _navigateToSelectedMovie = MutableStateFlow<Movie?>(value = null)
    val navigateToSelectedMovie: StateFlow<Movie?> = _navigateToSelectedMovie

    init {
        fetchMoviesUsingFlow()
    }

    private fun fetchMoviesUsingFlow() {
        viewModelScope.launch {
            repository.getNowPlayingMovies()
                .flowOn(Dispatchers.IO)
                .catch {
                    Timber.d("$LOG exception: $it")
                }
                .collect {
                    _nowPlayingMoviesFlow.value = Response.Success(it.movies)
                }
            repository.getPopularMovies()
                .flowOn(Dispatchers.IO)
                .catch {
                    Timber.d("$LOG exception: $it")
                }
                .collect {
                    _popularMoviesFlow.value = Response.Success(it.movies)
                }
            repository.getTopRatedMovies()
                .flowOn(Dispatchers.IO)
                .catch {
                    Timber.d("$LOG exception: $it")
                }
                .collect {
                    _topRatedMoviesFlow.value = Response.Success(it.movies)
                }

            repository.getUpcomingMovies()
                .flowOn(Dispatchers.IO)
                .catch {
                    Timber.d("$LOG exception: $it")
                }
                .collect {
                    _upcomingMoviesFlow.value = Response.Success(it.movies)
                }
        }
    }

    fun displayMovieDetails(movie: Movie) {
        Timber.e("$LOG clicked: ${movie.title}")
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }
}