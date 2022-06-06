package com.shalatan.entertainmentapp.ui.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shalatan.entertainmentapp.model.Movie

class DetailViewModelFactory(
    private val movie: Movie,
    private val detailRepository: DetailRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(movie, detailRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

