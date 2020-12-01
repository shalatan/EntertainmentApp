package com.shalatan.entertainmentapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val id: Int,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    @Json(name = "poster_path")
    val posterPath: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?,
    val is_watched: Boolean = false,
    val is_watch_later: Boolean = false
) : Parcelable