package com.shalatan.entertainmentapp.ui.moviesection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.shalatan.entertainmentapp.database.MovieDAO

class SavedContentViewModel(val database: MovieDAO, application: Application) :
    AndroidViewModel(application) {

    val watchedMovies = database.getAllWatchedMovies()
    val watchLaterMovies = database.getAllWatchLaterMovies()

}