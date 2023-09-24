package com.shalatan.entertainmentapp.utils

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daimajia.numberprogressbar.NumberProgressBar
import com.shalatan.entertainmentapp.MyApplication
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.database.SavedMovie

fun ImageView.loadImage(imgUrl: String?) {
    val fullUrl = Constants.IMG_BASE_URL + imgUrl
    fullUrl.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(this)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}

fun NumberProgressBar.loadMovieRating(savedMovie: SavedMovie) {
    if (savedMovie.isRated) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
        val highestWeight = MyApplication.highest.toFloat()
        val currentWeight = savedMovie.recommendationWeight.toFloat()
        val percentage: Float = (currentWeight / highestWeight) * 100
        this.progress = percentage.toInt()
        val color: Int = if (percentage > 70) {
            Color.GREEN
        } else if (percentage > 50) {
            Color.YELLOW
        } else {
            Color.RED
        }
        this.reachedBarColor = color
        this.setProgressTextColor(color)
    }
}