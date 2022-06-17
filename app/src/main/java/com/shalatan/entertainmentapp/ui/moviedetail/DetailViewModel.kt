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

    // The internal MutableLiveData String that stores the most recent response status
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the status String
    val status: LiveData<String>
        get() = _status

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

    /**
     * Request a toast by setting this value to true.
     */
    private var _showAddedToWatchedSnackbarEvent = MutableLiveData<Boolean>()
    private var _showAddedToWatchLaterSnackbarEvent = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately `show()` a toast and call `doneShowingSnackbar()`.
     */
    val showAddedToWatchedSnackbarEvent: LiveData<Boolean>
        get() = _showAddedToWatchedSnackbarEvent
    val showAddedToWatchLaterSnackbarEvent: LiveData<Boolean>
        get() = _showAddedToWatchLaterSnackbarEvent

    /**
     * Call this immediately after calling `show()` on a toast.
     * It will clear the toast request, so if the user rotates their phone it won't show a duplicate
     * toast.
     */
    fun doneShowingSnackbar() {
        _showAddedToWatchedSnackbarEvent.value = false
        _showAddedToWatchLaterSnackbarEvent.value = false
    }

    fun fetchMovieData(movie: Movie) {
        _selectedMovieDetail.value = movie
        fetchCurrentMovieDetails(movie)
    }

    private fun fetchCurrentMovieDetails(movie: Movie) {
        viewModelScope.launch {
            val getCompleteMovieDetail = repository.fetchCompleteMovieDataAsync(movie.id)
            try {
                val completeMovie = getCompleteMovieDetail.await()
                _completeMovieDetail.value = completeMovie
                Log.e(
                    "ABCD SYNOPSIS OF ${completeMovie.title} : ",
                    completeMovie.overview.toString()
                )
                _status.value = _completeMovieDetail.value!!.images?.backdrops.toString()
            } catch (exception: SocketTimeoutException) {
                Log.e("Error fetching movie timeout", "Hi")
            } catch (t: Throwable) {
                Log.e("Error Fetching Complete Movie Detail : ", t.message.toString())
                Log.e("Movie Name : ", movie.original_title.toString())
                _status.value = t.message
            }

        }
    }

    /**
     * add or replace existing data of movie to database with isWatched value true
     */
    fun addMovieToWatched() {
        viewModelScope.launch {
            Log.e("CLICKED", "ADDED TO WATCHED")
            val id = _selectedMovieDetail.value!!.id
            val poster = _selectedMovieDetail.value?.posterPath
            val name = _selectedMovieDetail.value?.original_title
            val savedMovie = SavedMovie(id, name, poster, isWatched = true, isWatchLater = false)
            repository.addMovieToWatched(savedMovie)
        }
        _showAddedToWatchedSnackbarEvent.value = true
    }

    /**
     * add or replace existing data of movie to database with isWatchlater value true
     */
    fun addMovieToWatchLater() {
        viewModelScope.launch {
            Log.e("CLICKED", "ADDED TO WATCH LATER")
            val id = _selectedMovieDetail.value!!.id
            val poster = _selectedMovieDetail.value?.posterPath
            val name = _selectedMovieDetail.value?.original_title
            val savedMovie = SavedMovie(id, name, poster, isWatched = false, isWatchLater = true)
            repository.addMovieToWatchlater(savedMovie)
        }
        _showAddedToWatchLaterSnackbarEvent.value = true
    }

}