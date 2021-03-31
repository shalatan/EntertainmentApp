package com.shalatan.entertainmentapp.ui.moviedetail

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shalatan.entertainmentapp.database.MovieDAO
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.model.*
import com.shalatan.entertainmentapp.network.TmdbApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class DetailViewModel(val database: MovieDAO, movie: Movie, app: Application) :
    AndroidViewModel(app) {

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
     *
     * It will clear the toast request, so if the user rotates their phone it won't show a duplicate
     * toast.
     */
    fun doneShowingSnackbar() {
        _showAddedToWatchedSnackbarEvent.value = false
        _showAddedToWatchLaterSnackbarEvent.value = false
    }

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        _selectedMovieDetail.value = movie
        fetchCurrentMovieDetails(movie)
    }

    /**
     * fetch complete movie details with retrofit
     */
    private fun fetchCurrentMovieDetails(movie: Movie) {
        coroutineScope.launch {
            val getCompleteMovieDetail =
                TmdbApi.RETROFIT_SERVICE.getCompleteMovieDetailAsync(_selectedMovieDetail.value!!.id)
            try {
                val completeMovie = getCompleteMovieDetail.await()
                _completeMovieDetail.value = completeMovie
                Log.e("MOVIE : ", _completeMovieDetail.value!!.toString())
                _status.value = _completeMovieDetail.value!!.images?.backdrops.toString()
            } catch (exception: SocketTimeoutException) {
                Toast.makeText(getApplication(),"Slow Network Connection",Toast.LENGTH_SHORT).show()
            }
            catch (t: Throwable) {
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
            insert(savedMovie)
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
            insert(savedMovie)
        }
        _showAddedToWatchLaterSnackbarEvent.value = true
    }

//    fun clearDatabase() {
//        viewModelScope.launch {
//            delete()
//        }
//    }

    private suspend fun delete() {
        database.clear()
    }

    private suspend fun insert(savedMovie: SavedMovie) {
        database.insert(savedMovie)
    }
}