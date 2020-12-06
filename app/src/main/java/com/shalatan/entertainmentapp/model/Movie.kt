package com.shalatan.entertainmentapp.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val genre_ids: List<Int> = emptyList(),
    val id: Int = -1,
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = -1.0,
    @Json(name = "poster_path")
    val posterPath: String = "",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = -1.0,
    val vote_count: Int = -1,
    val is_watched: Boolean = false,
    val is_watch_later: Boolean = false
) : Parcelable