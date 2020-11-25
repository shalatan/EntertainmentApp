package com.shalatan.entertainmentapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.themoviedb.org/3/movie/popular?api_key=ea9a49ebf2b74721a75aae271ebd3036

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.BASE_URL)
    .build()

interface LmdbApiService {
    @GET("3/movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Deferred<MovieResponse>

    @GET("3/movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Deferred<MovieResponse>
}

object LmdbApi{
    val retrofitService : LmdbApiService by lazy {
        retrofit.create(LmdbApiService::class.java)
    }
}