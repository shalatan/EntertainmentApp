package com.shalatan.entertainmentapp.ui.moviedetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shalatan.entertainmentapp.model.Movie

class DetailVIewModel(movie: Movie,app: Application) : AndroidViewModel(app) {

    private val _selectedMovie = MutableLiveData<Movie>()

    // The external LiveData for the SelectedProperty
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedMovie.value = movie
    }
}