package com.shalatan.entertainmentapp

import androidx.lifecycle.*
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel :
    ViewModel() {

    /**
     * Insert recommended movies(db will ignore insertion if duplicate happens) and then increase
     * each movies recommendationWeight with the rating given
     */
    fun recommendMovie(currentMovieId: Int, recommendedMovies: List<Movie>, rating: Float) {
        if (recommendedMovies.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                for (movie in recommendedMovies) {
                    val id = movie.id
                    val poster = movie.posterPath
                    val name = movie.original_title
                    val overview = movie.overview
                    Timber.d("ABCD Updating $name")
                    val savedMovie =
                        SavedMovie(
                            Id = id,
                            movieTitle = name,
                            moviePoster = poster,
                            movieOverview = overview,
                            isRated = false,
                            isWatchLater = false,
                            isRecommendationConsidered = false,
                            recommendationWeight = 0,
                            rating = 0f
                        )
//                    repository.insertMovie(savedMovie)
//                    repository.updateMovieRecommendationWeight(movieId = id, rating = rating)
                }
//                repository.changeRecommendationConsideredStatus(currentMovieId, true)
            }
        }
    }
}