package com.shalatan.entertainmentapp.network

import com.app.base.StateFullResult
import com.shalatan.entertainmentapp.model.MovieResponse

interface ApiHelper {

    suspend fun getNowPlayingMovies(): StateFullResult<MovieResponse>
}