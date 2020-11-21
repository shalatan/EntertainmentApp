package com.shalatan.entertainmentapp.network

data class MovieResponse(
        val page: Int,
        val movieResults: MutableList<MovieResult>,
        val total_pages: Int,
        val total_results: Int
)