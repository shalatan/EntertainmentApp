package com.shalatan.entertainmentapp

import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.piasy.biv.view.BigImageView
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.model.Cast
import com.shalatan.entertainmentapp.model.Movie
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

//bind recycler view adapter for fragment_overview
@BindingAdapter("castListData")
fun bindMovieCastRecyclerView(recyclerView: RecyclerView, data: List<Cast>?) {
    val movieCastAdapter = recyclerView.adapter as MovieCastAdapter
    movieCastAdapter.submitList(data)
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
fun bindPoster(bigImageView: BigImageView, imgUrl: String?) {
    val fullUrl = Constants.IMG_BASE_URL_O + imgUrl
    fullUrl.let {
        bigImageView.showImage(Uri.parse(fullUrl))
    }
}

