package com.shalatan.entertainmentapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "saved_movies_table")
data class Movie(
    @Ignore
    val adult: Boolean?,
    @Ignore
    val backdrop_path: String?,
    @Ignore
    val genre_ids: List<Int>?,
    @PrimaryKey
    val id: Int,
    val original_language: String?,
    val original_title: String?,
    @Ignore
    val overview: String?,
    @Ignore
    val popularity: Double?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Ignore
    val release_date: String?,
    @Ignore
    val title: String?,
    @Ignore
    val video: Boolean?,
    @Ignore
    val vote_average: Double?,
    @Ignore
    val vote_count: Int?,
    val is_watched: Boolean = false,
    val is_watch_later: Boolean = false
) : Parcelable