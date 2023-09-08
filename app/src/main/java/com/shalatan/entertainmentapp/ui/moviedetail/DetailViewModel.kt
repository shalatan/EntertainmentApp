package com.shalatan.entertainmentapp.ui.moviedetail

import androidx.lifecycle.*
import com.shalatan.entertainmentapp.MyApplication
import com.shalatan.entertainmentapp.database.DatabaseRepository
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.model.*
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
class DetailViewModel @Inject constructor(
    private val repository: DatabaseRepository,
    private val networkRepository: NetworkRepository
) :
    ViewModel() {

    private val LOG = MyApplication.LOG

    private val _selectedMovieDetail = MutableLiveData<Movie>()
    val selectedMovieDetail: LiveData<Movie>
        get() = _selectedMovieDetail

    private val _completeMovieDetailFlow = MutableStateFlow<CompleteMovieDetail?>(value = null)
    val completeMovieDetailFlow: StateFlow<CompleteMovieDetail?> = _completeMovieDetailFlow

    private val _recommendedMoviesFlow = MutableStateFlow<List<Movie>>(emptyList())
    val recommendedMoviesFlow: StateFlow<List<Movie>> = _recommendedMoviesFlow

    private val _isMovieExistInWatchedList = MutableLiveData<Boolean>()
    val isMovieExistInWatchedList: LiveData<Boolean>
        get() = _isMovieExistInWatchedList

    private val _isMovieExistInWatchLaterList = MutableLiveData<Boolean>()
    val isMovieExistInWatchLaterList: LiveData<Boolean>
        get() = _isMovieExistInWatchLaterList

    fun fetchMovieData(movie: Movie) {
        _selectedMovieDetail.value = movie
//        fetchCurrentMovieDetails(movie)
        fetchCompleteMovieData(movie)
    }

    private fun fetchCompleteMovieData(movie: Movie) {
        Timber.d("$LOG completeMovieDetail Id: ${movie.id}")
        viewModelScope.launch {
            networkRepository.getMovieCompleteData(movieId = movie.id)
                .flowOn(Dispatchers.IO)
                .catch {
                    Timber.d("$LOG exception: $it")
                }
                .collect {
                    _completeMovieDetailFlow.value = it
                    Timber.d("$LOG completeMovieDetail: $it")
                }
            networkRepository.getSimilarMovies(movieId = movie.id)
                .flowOn(Dispatchers.IO)
                .catch {
                    Timber.d("$LOG exception: $it")
                }
                .collect {
                    _recommendedMoviesFlow.value = it.movies
                    Timber.d("$LOG similarMovies: ${it.movies.size}")
                }
        }
    }

    /**
     * add or replace existing data of movie to database with isWatchlater value true
     */
    fun addMovieToWatchList(isRated: Boolean, isWatchLater: Boolean) {
        viewModelScope.launch {
            val id = _selectedMovieDetail.value!!.id
            val poster = _selectedMovieDetail.value?.posterPath
            val name = _selectedMovieDetail.value?.original_title
            val overview = _selectedMovieDetail.value?.overview
            val savedMovie =
                SavedMovie(
                    Id = id,
                    movieTitle = name,
                    moviePoster = poster,
                    movieOverview = overview,
                    isRated = isRated,
                    isWatchLater = isWatchLater,
                    isRecommendationConsidered = false,
                    recommendationWeight = 0,
                    rating = 0f
                )
            repository.insertMovie(savedMovie)
        }
    }

    /**
     * check if current movie exists in watched or watchlater list
     */
    fun isMovieSavedInWatchList(movieId: Int) {
        viewModelScope.launch {
            val isMovieWatched = repository.isMovieInWatchedList(movieId)
            val isMovieWatchLater = repository.isMovieInWatchLaterList(movieId)
            _isMovieExistInWatchedList.value = isMovieWatched != 0
            _isMovieExistInWatchLaterList.value = isMovieWatchLater != 0
        }
    }

    fun updateWatchLaterStatus(isWatchLater: Boolean) {
        val id = _selectedMovieDetail.value!!.id
        viewModelScope.launch {
            repository.changeMovieWatchLaterStatus(id, isWatchLater)
        }
    }

    fun updateRatedStatus(isRated: Boolean, rating: Float) {
        val id = _selectedMovieDetail.value!!.id
        viewModelScope.launch {
            repository.changeMovieRatedStatus(id, isRated, rating)
        }
    }
}