package com.shalatan.entertainmentapp.ui.moviedetail

import android.util.Log
import androidx.lifecycle.*
import com.shalatan.entertainmentapp.database.DatabaseRepository
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.model.*
import com.shalatan.entertainmentapp.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DatabaseRepository,
    private val networkRepository: NetworkRepository
) :
    ViewModel() {

    private val _selectedMovieDetail = MutableLiveData<Movie>()
    val selectedMovieDetail: LiveData<Movie>
        get() = _selectedMovieDetail

    private val _completeMovieDetail = MutableLiveData<CompleteMovieDetail>()
    val completeMovieDetail: LiveData<CompleteMovieDetail>
        get() = _completeMovieDetail

    private val _recommendedMovies = MutableLiveData<List<Movie>>()
    val recommendedMovies: LiveData<List<Movie>>
        get() = _recommendedMovies

    private val _isMovieExistInWatchedList = MutableLiveData<Boolean>()
    val isMovieExistInWatchedList: LiveData<Boolean>
        get() = _isMovieExistInWatchedList

    private val _isMovieExistInWatchLaterList = MutableLiveData<Boolean>()
    val isMovieExistInWatchLaterList: LiveData<Boolean>
        get() = _isMovieExistInWatchLaterList

    fun fetchMovieData(movie: Movie) {
        _selectedMovieDetail.value = movie
//        fetchCurrentMovieDetails(movie)
    }

    private fun fetchCurrentMovieDetails(movie: Movie) {
        viewModelScope.launch {
            val getCompleteMovieDetail = networkRepository.fetchCompleteMovieDataAsync(movie.id)
            val similarMovies = networkRepository.fetchSimilarMoviesDataAsync(movie.id)
            try {
                _completeMovieDetail.value = getCompleteMovieDetail.await()
                _recommendedMovies.value = similarMovies.await().movies
            } catch (exception: SocketTimeoutException) {
                Log.e("Error fetching movie timeout", "Hi")
            } catch (t: Throwable) {
                Log.e("Error Fetching Complete Movie Detail : ", t.message.toString())
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