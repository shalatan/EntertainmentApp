package com.shalatan.entertainmentapp.ui.moviesection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SavedContentViewModelFactory(
    private val dataSource: SavedContentRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedContentViewModel::class.java)) {
            return SavedContentViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}