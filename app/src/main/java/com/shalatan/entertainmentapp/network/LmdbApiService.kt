package com.shalatan.entertainmentapp.network

import com.shalatan.entertainmentapp.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface LmdbApiService {
    @GET("3/movie/popular")
    fun getProperties(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<String>
}

object LmdbApi{
    val retrofitService : LmdbApiService by lazy {
        retrofit.create(LmdbApiService::class.java)
    }
}