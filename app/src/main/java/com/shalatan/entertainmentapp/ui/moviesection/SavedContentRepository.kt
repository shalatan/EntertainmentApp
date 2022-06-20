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

    suspend fun deleteMovieFromDatabase(savedMovie: SavedMovie) {
        dao.delete(savedMovie)
    }
}


