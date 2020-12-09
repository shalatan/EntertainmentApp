package com.shalatan.entertainmentapp.ui.moviedetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shalatan.entertainmentapp.model.Backdrop
import com.shalatan.entertainmentapp.model.ImagesResponse
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.model.Poster
import com.shalatan.entertainmentapp.network.LmdbApi
import kotlinx.coroutines.*

class PosterViewModel(app: Application, val movie: Movie) : AndroidViewModel(app) {

    private val _selectedMovieDetail = MutableLiveData<Movie>()
    val selectedMovieDetail: LiveData<Movie>
        get() = _selectedMovieDetail

    private val _images = MutableLiveData<ImagesResponse>()
    val images: LiveData<ImagesResponse>
        get() = _images

    private val _posters = MutableLiveData<Poster>()
    val posters: LiveData<Poster>
        get() = _posters

    private val _backdrops = MutableLiveData<Backdrop>()
    val backdrops: LiveData<Backdrop>
        get() = _backdrops

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _selectedMovieDetail.value = movie
        fetchMoviePosters()
    }

    private fun fetchMoviePosters() {
        coroutineScope.launch {
            val getMoviePosters =
                LmdbApi.retrofitService.getSelectedMovieImages(_selectedMovieDetail.value!!.id)
            try {
                val imagesResponse = getMoviePosters.await()
                _images.value = imagesResponse
            } catch (t: Throwable) {
                Log.e("Error fetching complete detail : ", t.message.toString())
            }
        }
    }
}