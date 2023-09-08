package com.shalatan.entertainmentapp.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalatan.entertainmentapp.MyApplication
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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

    private val _nowPlayingMoviesFlow = MutableStateFlow<List<Movie>>(value = emptyList())
    val nowPlayingMoviesFlow: StateFlow<List<Movie>> = _nowPlayingMoviesFlow

    private val _popularMoviesFlow = MutableStateFlow<List<Movie>>(value = emptyList())
    val popularMoviesFlow: StateFlow<List<Movie>> = _popularMoviesFlow

    private val _topRatedMoviesFlow = MutableStateFlow<List<Movie>>(value = emptyList())
    val topRatedMoviesFlow: StateFlow<List<Movie>> = _topRatedMoviesFlow

    private val _upcomingMoviesFlow = MutableStateFlow<List<Movie>>(value = emptyList())
    val upcomingMoviesFlow: StateFlow<List<Movie>> = _upcomingMoviesFlow

    private val _navigateToSelectedMovie = MutableLiveData<Movie?>()
    val navigateToSelectedMovie: LiveData<Movie?>
        get() = _navigateToSelectedMovie

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
                    _nowPlayingMoviesFlow.value = it.movies
                }

            repository.getPopularMovies()
                .flowOn(Dispatchers.IO)
                .catch {
                    Timber.d("$LOG exception: $it")
                }
                .collect {
                    _popularMoviesFlow.value = it.movies
                }
            repository.getTopRatedMovies()
                .flowOn(Dispatchers.IO)
                .catch {
                    Timber.d("$LOG exception: $it")
                }
                .collect {
                    _topRatedMoviesFlow.value = it.movies
                }

            repository.getUpcomingMovies()
                .flowOn(Dispatchers.IO)
                .catch {
                    Timber.d("$LOG exception: $it")
                }
                .collect {
                    _upcomingMoviesFlow.value = it.movies
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