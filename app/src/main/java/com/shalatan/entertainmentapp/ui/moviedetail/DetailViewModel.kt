package com.shalatan.entertainmentapp.ui.moviedetail

import android.util.Log
import androidx.lifecycle.*
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository) :
    ViewModel() {

    private val _selectedMovieDetail = MutableLiveData<Movie>()
    val selectedMovieDetail: LiveData<Movie>
        get() = _selectedMovieDetail

    private val _completeMovieDetail = MutableLiveData<CompleteMovieDetail>()
    val completeMovieDetail: LiveData<CompleteMovieDetail>
        get() = _completeMovieDetail

    private val _navigateToPosterFragment = MutableLiveData<Movie>()
    val navigateToPosterFragment: LiveData<Movie>
        get() = _navigateToPosterFragment

    private val _backdropImages = MutableLiveData<Backdrop>()
    val backdropImages: LiveData<Backdrop>
        get() = _backdropImages

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
        fetchCurrentMovieDetails(movie)
    }

    private fun fetchCurrentMovieDetails(movie: Movie) {
        viewModelScope.launch {
            val getCompleteMovieDetail = repository.fetchCompleteMovieDataAsync(movie.id)
            val similarMovies = repository.fetchSimilarMoviesDataAsync(movie.id)
            try {
                val completeMovie = getCompleteMovieDetail.await()
                _completeMovieDetail.value = completeMovie
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
            val savedMovie =
                SavedMovie(id, name, poster, isRated = isRated, isWatchLater = isWatchLater)
            repository.insertMovie(savedMovie)
        }
    }

    /**
     * update existing data of movie to database
     */
    fun updateExistingMovieData(isWatchLater: Boolean, isRated: Boolean) {
        viewModelScope.launch {
            val id = _selectedMovieDetail.value!!.id
            val poster = _selectedMovieDetail.value?.posterPath
            val name = _selectedMovieDetail.value?.original_title
            val savedMovie =
                SavedMovie(id, name, poster, isRated = isRated, isWatchLater = isWatchLater)
            repository.updateMovie(savedMovie)
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

}