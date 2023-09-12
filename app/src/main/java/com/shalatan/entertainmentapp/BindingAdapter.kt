package com.shalatan.entertainmentapp

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daimajia.numberprogressbar.NumberProgressBar
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.utils.Constants

@BindingAdapter("setUpRec")
fun bindMovieRecommendationPercentage(
    progressBar: NumberProgressBar,
    savedMovie: SavedMovie
) {
    if (savedMovie.isRated) {
        progressBar.visibility = View.GONE
    } else {
        progressBar.visibility = View.VISIBLE
        val highestWeight = MyApplication.highest.toFloat()
        val currentWeight = savedMovie.recommendationWeight.toFloat()
        val percentage: Float = (currentWeight / highestWeight) * 100
        progressBar.progress = percentage.toInt()
        val color: Int = if (percentage > 70) {
            Color.GREEN
        } else if (percentage > 50) {
            Color.YELLOW
        } else {
            Color.RED
        }
        progressBar.reachedBarColor = color
        progressBar.setProgressTextColor(color)
    }
}

/* Bind Images */

//bind image into movie_item
@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: String?) {
    val fullUrl = Constants.IMG_BASE_URL + imgUrl
    fullUrl.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imageView)
    }
}

//bind poster into view_pager_item
@BindingAdapter("posterImageUrl")
fun bindPoster(bigImageView: ImageView, imgUrl: String?) {
    val fullUrl = Constants.IMG_BASE_URL_O + imgUrl
    fullUrl.let {
        Glide.with(bigImageView.context)
            .load(fullUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(bigImageView)
    }
}

