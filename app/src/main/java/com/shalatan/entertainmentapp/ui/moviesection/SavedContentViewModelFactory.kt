package com.shalatan.entertainmentapp.ui.moviesection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shalatan.entertainmentapp.database.MovieDAO

class SavedContentViewModelFactory(
    private val dataSource: MovieDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedContentViewModel::class.java)) {
            return SavedContentViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}