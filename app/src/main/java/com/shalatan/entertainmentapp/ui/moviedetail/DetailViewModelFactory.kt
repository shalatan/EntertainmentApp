package com.shalatan.entertainmentapp.ui.moviedetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shalatan.entertainmentapp.database.MovieDAO
import com.shalatan.entertainmentapp.model.Movie

class DetailViewModelFactory(
    private val dataSource: MovieDAO,
    private val movie: Movie,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dataSource, movie, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
