package com.shalatan.entertainmentapp

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daimajia.numberprogressbar.NumberProgressBar
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.model.Cast
import com.shalatan.entertainmentapp.model.Genre
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.ui.moviedetail.GenreAdapter
import com.shalatan.entertainmentapp.ui.moviedetail.MovieCastAdapter
import com.shalatan.entertainmentapp.ui.moviesection.SavedContentAdapter
import com.shalatan.entertainmentapp.ui.overview.MovieAdapter
import com.shalatan.entertainmentapp.utils.Constants

//bind recycler view adapter for network movies list
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieAdapter
    adapter.submitList(data)
}

//bind recycler view adapter for local database movies list
@BindingAdapter("savedContent")
fun bindFavouriteRecyclerView(recyclerView: RecyclerView, data: List<SavedMovie>?) {
    val adapter = recyclerView.adapter as SavedContentAdapter
    adapter.submitList(data)
}

@BindingAdapter("movieCastList")
fun bindMovieCastRecyclerView(recyclerView: RecyclerView, data: List<Cast>?) {
    val movieCastAdapter = recyclerView.adapter as MovieCastAdapter
    movieCastAdapter.submitList(data?.sortedByDescending { it.popularity })
}

@BindingAdapter("movieGenreList")
fun bindMovieGenreRecyclerView(recyclerView: RecyclerView, data: List<Genre>?) {
    val movieGenreAdapter = recyclerView.adapter as GenreAdapter
    movieGenreAdapter.submitList(data)
}

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
//        bigImageView.showImage(Uri.parse(fullUrl))
        Glide.with(bigImageView.context)
            .load(fullUrl)
            .into(bigImageView)
    }
}

//@BindingAdapter("adultChecker")
//fun adultCheck(view: View, adult: Boolean) {
//    Log.e("Testing Adult Check : ", adult.toString())
//    if (adult) {
//        view.setBackgroundResource(R.drawable.adult_true)
//    } else {
//        view.setBackgroundResource(R.drawable.adult_false)
//    }
//}

