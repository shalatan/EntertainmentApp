package com.shalatan.entertainmentapp.network

import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.utils.Constants
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//movies list - https://api.themoviedb.org/3/movie/popular?api_key={api_key}
//complete movie details - https://api.themoviedb.org/3/movie/216015?api_key={api_key}&append_to_response=videos,images,reviews
//search - https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher

interface TmdbApiService {

    //add region
    @GET("3/movie/now_playing?api_key=ea9a49ebf2b74721a75aae271ebd3036")
    fun getNowPlayingMoviesAsync(
        @Query("page") page: Int = 1
    ): Deferred<MovieResponse>

    @GET("3/movie/popular?api_key=ea9a49ebf2b74721a75aae271ebd3036")
    fun getPopularMoviesAsync(
//        @Query("region") lang: String = Constants.REGION_INDIA,
        @Query("page") page: Int = 1
    ): Deferred<MovieResponse>

    @GET("3/movie/top_rated?api_key=ea9a49ebf2b74721a75aae271ebd3036")
    fun getTopRatedMoviesAsync(
//        @Query("region") lang: String = Constants.REGION_INDIA,
        @Query("page") page: Int = 1
    ): Deferred<MovieResponse>

    //add region
    @GET("3/movie/upcoming?api_key=ea9a49ebf2b74721a75aae271ebd3036")
    fun getUpcomingMoviesAsync(
//        @Query("region") lang: String = Constants.REGION_INDIA,
        @Query("page") page: Int = 1
    ): Deferred<MovieResponse>

    @GET("3/movie/{movieId}")
    fun getCompleteMovieDetailAsync(
        @Path("movieId") movieID: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("append_to_response") atr: String = Constants.VIR
    ): Deferred<CompleteMovieDetail>

    @GET("3/search/movie")
    fun getSearchedMovieAsync(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("query") search: String
    ): Deferred<MovieResponse>
}