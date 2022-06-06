package com.shalatan.entertainmentapp.ui.moviedetail

import com.shalatan.entertainmentapp.database.MovieDAO
import com.shalatan.entertainmentapp.database.SavedMovie

class DetailRepository(private val dao: MovieDAO) {

    suspend fun addMovieToWatched(savedMovie: SavedMovie) {
        dao.insert(savedMovie)
    }

    suspend fun addMovieToWatchlater(savedMovie: SavedMovie) {
        dao.insert(savedMovie)
    }
}


