package com.shalatan.entertainmentapp.ui.moviedetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.network.LmdbApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailVIewModel(movie: Movie,app: Application) : AndroidViewModel(app) {

    // The internal MutableLiveData String that stores the most recent response status
    private val _status = MutableLiveData<String>()
    // The external immutable LiveData for the status String
    val status: LiveData<String>
        get() = _status

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    private val _completeMovieDetail = MutableLiveData<CompleteMovieDetail>()

    val completeMovieDetail: LiveData<CompleteMovieDetail>
        get() = _completeMovieDetail

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedMovie.value = movie
        fetchCurrentMovieDetails()
    }

    private fun fetchCurrentMovieDetails() {
        coroutineScope.launch {
            Log.e("Calling Coroutine with ID : ",_selectedMovie.value!!.id.toString())
            val getCompleteMovieDetail = LmdbApi.retrofitService.getCompleteMovieDetail(_selectedMovie.value!!.id)
            Log.e("Deferred : ",getCompleteMovieDetail.toString())
            try {
                val completeMovie = getCompleteMovieDetail.await()
                Log.e("Deferred : ",getCompleteMovieDetail.toString())
                Log.e("Deferred : ",completeMovie.toString())
                _completeMovieDetail.value = completeMovie
            }catch (t: Throwable){
                Log.e("Deferred : ",getCompleteMovieDetail.toString())
                Log.e("Deferred : ",t.message.toString())
                _status.value = t.message
            }
        }
    }
}