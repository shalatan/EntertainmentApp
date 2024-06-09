package com.shalatan.entertainmentapp.model

data class CompleteMovieDetail(

    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val belongs_to_collection: BelongsToCollection? = null,
    val budget: Int? = null,
    val credits: Credits? = null,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Int? = null,
    val images: Images? = null,
    val imdb_id: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val revenue: Double? = null,
    val runtime: Int? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val videos: Videos? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
)