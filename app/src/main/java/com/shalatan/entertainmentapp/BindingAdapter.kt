package com.shalatan.entertainmentapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shalatan.entertainmentapp.utils.Constants

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

