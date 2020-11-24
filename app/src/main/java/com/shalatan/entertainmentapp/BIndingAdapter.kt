package com.shalatan.entertainmentapp

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shalatan.entertainmentapp.adapters.MovieAdapter
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.utils.Constants


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,data: List<Movie>?){
    val adapter = recyclerView.adapter as MovieAdapter
    adapter.submitList(data)
}


//execute this binding adapter when the xml item has imgUrl attribute
@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: String?) {
    val fullUrl = Constants.IMG_BASE_URL+imgUrl
    fullUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imageView)
    }
}