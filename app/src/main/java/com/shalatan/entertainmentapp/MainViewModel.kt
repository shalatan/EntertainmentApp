package com.shalatan.entertainmentapp

import android.util.Log
import androidx.lifecycle.*
import com.shalatan.entertainmentapp.database.DatabaseRepository
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val networkRepository: NetworkRepository
) :
    ViewModel() {

    /**
     * Step 1 - Clear All Movies Except Rated and Watch-Later Movies
     * Step 2 - Make RecommendationWight = 0 for Watch-Later Movies
     * Step 3 - Get All Rated Movies By User From Database
     * Step 4 - Get Similar Movies From API For Each Rated Movie
     * Step 5 - call the recommendedMovie() For Each Similar Movie
     */
//    fun refreshRecommendations() {
//        CoroutineScope(Dispatchers.IO).launch {
//            //step1
//            databaseRepository.clearResidueMovies()
//            //step2
//            databaseRepository.clearRecommendationWeightForWatchLaterMovies()
//            //step3
//            val watchedMovies = databaseRepository.getAllRatedMoviesList()
//            for (movie in watchedMovies) {
//                val movieId = movie.Id
//                val recommendedMoviesDeferred =
//                    networkRepository.fetchSimilarMoviesDataAsync(movieId)
//                try {
//                    //step4
//                    val recommendedMovies = recommendedMoviesDeferred.await().movies
//                    //step5
//                    recommendMovie(movieId, recommendedMovies, movie.rating)
//                } catch (t: Throwable) {
//                    Log.e("Error Fetching Complete Movie Detail : ", t.message.toString())
//                }
//            }
//            val newRecommendedMovies = databaseRepository.getAllRecommendedMovies().value
//            val highest = newRecommendedMovies?.get(0)?.recommendationWeight ?: 0
//            MyApplication.highest = highest
//        }
//    }

    /**
     * Insert the recommended movie(db will ignore insertion if duplicate happens) and then increase
     * it's recommendationWeight with the rating given
     */
    fun recommendMovie(currentMovieId: Int, recommendedMovies: List<Movie>, rating: Float) {
        if (recommendedMovies.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                for (movie in recommendedMovies) {
                    val id = movie.id
                    val poster = movie.posterPath
                    val name = movie.original_title
                    val overview = movie.overview
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
                    databaseRepository.insertMovie(savedMovie)
                    databaseRepository.updateMovieRecommendationWeight(
                        movieId = id,
                        rating = rating
                    )
                }
                databaseRepository.changeRecommendationConsideredStatus(currentMovieId, true)
            }
        }
    }
}