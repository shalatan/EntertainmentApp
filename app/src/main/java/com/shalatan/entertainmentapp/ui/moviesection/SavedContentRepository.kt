package com.shalatan.entertainmentapp.ui.moviesection

import androidx.lifecycle.LiveData
import com.shalatan.entertainmentapp.database.MovieDAO
import com.shalatan.entertainmentapp.database.SavedMovie

class SavedContentRepository(private val dao: MovieDAO) {

    fun getAllWatchedMovies(): LiveData<List<SavedMovie>> {
        return dao.getAllWatchedMovies()
    }

    fun getAllWatchLaterMovies(): LiveData<List<SavedMovie>> {
        return dao.getAllWatchedMovies()
    }

    suspend fun deleteMovieFromDatabase(savedMovie: SavedMovie) {
        dao.delete(savedMovie)
    }
}


