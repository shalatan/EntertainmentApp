package com.shalatan.entertainmentapp.network

import com.shalatan.entertainmentapp.model.MovieResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.themoviedb.org/3/movie/popular?api_key=ea9a49ebf2b74721a75aae271ebd3036
private const val BASE_URL = "https://api.themoviedb.org/"
private const val API_KEY = "ea9a49ebf2b74721a75aae271ebd3036"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface lmdbApiService {

    @GET("3/movie/popular")
     fun getPopularMovies(
        @Query("api_key") api_key: String = "ea9a49ebf2b74721a75aae271ebd3036"
    ): Call<String>

    @GET("movie/top-rated")
     fun getTopRatedMovies(
        @Query("api_key")
        api_key: String = "ea9a49ebf2b74721a75aae271ebd3036"
    ): Call<String>
}


object lmdbApi {
    val retrofitService: lmdbApiService by lazy { retrofit.create(lmdbApiService::class.java) }
}