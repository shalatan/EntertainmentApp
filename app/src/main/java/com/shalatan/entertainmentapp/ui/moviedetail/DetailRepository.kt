package com.shalatan.entertainmentapp.ui.moviedetail

import com.shalatan.entertainmentapp.database.MovieDAO
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.network.TmdbApiService
import com.shalatan.entertainmentapp.utils.Constants
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val dao: MovieDAO,
    private val tmdbApiService: TmdbApiService
) {

    suspend fun addMovieToWatched(savedMovie: SavedMovie) {
        dao.insert(savedMovie)
    }

    suspend fun addMovieToWatchlater(savedMovie: SavedMovie) {
        dao.insert(savedMovie)
    }

    fun fetchCompleteMovieDataAsync(movieId: Int): Deferred<CompleteMovieDetail> {
        return tmdbApiService.getCompleteMovieDetailAsync(movieId, Constants.API_KEY, Constants.VIR)
    }
}


