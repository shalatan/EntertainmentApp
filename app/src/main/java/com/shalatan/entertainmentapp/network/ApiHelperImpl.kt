package com.shalatan.entertainmentapp.network

import com.app.base.StateFullResult
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.utils.toStateFullResult
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getNowPlayingMovies(): StateFullResult<MovieResponse> {
        return apiService.newGetNowPlayingMovies(1).toStateFullResult()
    }
}