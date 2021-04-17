package com.shalatan.entertainmentapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

//movies list - https://api.themoviedb.org/3/movie/popular?api_key={api_key}
//complete movie details - https://api.themoviedb.org/3/movie/216015?api_key={api_key}&append_to_response=videos,images,reviews
//search - https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val httpLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .connectTimeout(1, TimeUnit.MINUTES) // connect timeout
    .writeTimeout(1, TimeUnit.MINUTES) // write timeout
    .readTimeout(1, TimeUnit.MINUTES) // read timeout
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.BASE_URL)
    .client(okHttpClient)
    .build()


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
    fun getSearchedMovie(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("query") search: String
    ): Deferred<MovieResponse>
}

object TmdbApi {
    val RETROFIT_SERVICE: TmdbApiService by lazy {
        retrofit.create(TmdbApiService::class.java)
    }
}