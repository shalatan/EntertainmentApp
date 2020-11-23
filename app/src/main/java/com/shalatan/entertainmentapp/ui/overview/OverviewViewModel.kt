package com.shalatan.entertainmentapp.ui.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.network.LmdbApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {


    // The internal MutableLiveData String that stores the most recent response status
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the status String
    val status: LiveData<String>
        get() = _status

    private val _popularMovies = MutableLiveData<MovieResponse>()
    val popularMovies: LiveData<MovieResponse>
        get() = _popularMovies

    private val _singleMovie = MutableLiveData<Movie>()
    val singleMovie: LiveData<Movie>
        get() = _singleMovie

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Log.d("OverviewViewModel : ", "Created")
        fetchJSON()
    }

    private fun fetchJSON() {
        coroutineScope.launch {
            var getPropertiesDeferred = LmdbApi.retrofitService.getProperties()
            try {
                var listResult = getPropertiesDeferred.await()
                _singleMovie.value = listResult.movies[0]
            } catch (t: Throwable) {
                _status.value = "Failure" + t.message

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}