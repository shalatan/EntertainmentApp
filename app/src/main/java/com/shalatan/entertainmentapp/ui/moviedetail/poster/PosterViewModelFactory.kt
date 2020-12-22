package com.shalatan.entertainmentapp.ui.moviedetail.poster

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PosterViewModelFactory(
    private val posterPath: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PosterViewModel::class.java)) {
            return PosterViewModel(application, posterPath) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
