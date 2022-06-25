package com.shalatan.entertainmentapp.ui.moviesection

import androidx.lifecycle.LiveData
import com.shalatan.entertainmentapp.database.MovieDAO
import com.shalatan.entertainmentapp.database.SavedMovie
import javax.inject.Inject

class SavedContentRepository @Inject constructor(private val dao: MovieDAO) {

    fun getAllWatchedMovies(): LiveData<List<SavedMovie>> {
        return dao.getAllWatchedMovies()
    }

    fun getAllWatchLaterMovies(): LiveData<List<SavedMovie>> {
        return dao.getAllWatchLaterMovies()
    }

    suspend fun changeMovieWatchLaterStatus(movieId: Int, isWatchLater: Boolean) {
        dao.changeWatchLaterStatus(movieId, isWatchLater)
    }

    suspend fun changeMovieRatedStatus(movieId: Int, isRated: Boolean, rating: Float) {
        dao.changeRatedStatus(movieId, isRated, rating)
    }
}


