package com.shalatan.entertainmentapp

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.model.Cast
import com.shalatan.entertainmentapp.model.Genre
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.ui.moviedetail.GenreAdapter
import com.shalatan.entertainmentapp.ui.moviedetail.MovieCastAdapter
import com.shalatan.entertainmentapp.ui.moviesection.SavedContentAdapter
import com.shalatan.entertainmentapp.ui.overview.MovieAdapter
import com.shalatan.entertainmentapp.utils.Constants

/* Bind List Views */

//bind recycler view adapter for fragment_overview
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieAdapter
    adapter.submitList(data)
//    adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
}

//bind recycler view adapter for fragment_overview
//@BindingAdapter("landscapeListData")
//fun bindLandscapeRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
//    val adapter = recyclerView.adapter as LandscapeMovieAdapter
//    adapter.submitList(data)
//}

//bind recycler view adapter for fragments_(watched/watch_later)_movies
@BindingAdapter("savedContent")
fun bindFavouriteRecyclerView(recyclerView: RecyclerView, data: List<SavedMovie>?) {
    val adapter = recyclerView.adapter as SavedContentAdapter
    adapter.submitList(data)
}

@BindingAdapter("movieCastList")
fun bindMovieCastRecyclerView(recyclerView: RecyclerView, data: List<Cast>?) {
    val movieCastAdapter = recyclerView.adapter as MovieCastAdapter
    movieCastAdapter.submitList(data)
}

@BindingAdapter("movieGenreList")
fun bindMovieGenreRecyclerView(recyclerView: RecyclerView, data: List<Genre>?) {
    val movieGenreAdapter = recyclerView.adapter as GenreAdapter
    movieGenreAdapter.submitList(data)
}

/* Bind Images */

//bind image into movie_item
@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: String?) {
    val fullUrl = Constants.IMG_BASE_URL + imgUrl
//    fullUrl?.let{
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

@BindingAdapter("adultChecker")
fun adultCheck(view: View, adult: Boolean) {
    Log.e("Testing Adult Check : ", adult.toString())
    if (adult) {
        view.setBackgroundResource(R.drawable.adult_true)
    } else {
        view.setBackgroundResource(R.drawable.adult_false)
    }
}

