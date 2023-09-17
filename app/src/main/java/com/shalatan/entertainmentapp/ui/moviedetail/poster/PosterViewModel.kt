package com.shalatan.entertainmentapp.ui.moviedetail.poster

import android.app.Application
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.shalatan.entertainmentapp.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PosterViewModel(val app: Application, private val posterPath: String) :
    AndroidViewModel(app) {

    private val _posterURL = MutableLiveData<String>()
    val posterURL: LiveData<String>
        get() = _posterURL

    private var _posterSetAsWallpaperSnackbarEvent = MutableStateFlow<Boolean>(value = false)

    val posterSetAsWallpaperSnackbarEvent: StateFlow<Boolean> = _posterSetAsWallpaperSnackbarEvent

    fun doneShowingSnackbar() {
        _posterSetAsWallpaperSnackbarEvent.value = false
    }


    init {
        _posterURL.value = posterPath
    }

    /**
     * set the current position element of backdrops as wallpaper and trigger snackbar
     */
    fun setImageAsWallpaper(poster: String?) {
        val fullUrl = Constants.IMG_BASE_URL_O + poster
        Glide.with(app).asBitmap().load(fullUrl).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap, transition: Transition<in Bitmap>?
                ) {
                    WallpaperManager.getInstance(app).setBitmap(resource)
                    _posterSetAsWallpaperSnackbarEvent.value = true
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}