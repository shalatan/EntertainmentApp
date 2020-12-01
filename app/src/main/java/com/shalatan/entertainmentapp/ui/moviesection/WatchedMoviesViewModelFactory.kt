package com.shalatan.entertainmentapp.ui.moviesection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shalatan.entertainmentapp.database.MovieDAO

class WatchedMoviesViewModelFactory(private val dataSource: MovieDAO, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WatchedMoviesViewModel::class.java)) {
            return WatchedMoviesViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}