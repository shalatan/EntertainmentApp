package com.shalatan.entertainmentapp.model

import com.squareup.moshi.Json

data class MovieResponse(
    val page: Int,
    @Json(name = "results") val movies: MutableList<Movie>,
    val total_pages: Int,
    val total_results: Int
)