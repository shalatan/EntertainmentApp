package com.shalatan.entertainmentapp.ui.moviesection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.shalatan.entertainmentapp.database.MovieDAO

class WatchedMoviesViewModel(val database: MovieDAO, application: Application) :
    AndroidViewModel(application) {

}