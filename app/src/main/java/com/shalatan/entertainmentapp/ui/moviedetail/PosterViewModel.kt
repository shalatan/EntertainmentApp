package com.shalatan.entertainmentapp.ui.moviedetail

import android.app.Application
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import com.shalatan.entertainmentapp.model.Backdrop
import com.shalatan.entertainmentapp.model.ImagesResponse
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.model.Poster
import com.shalatan.entertainmentapp.network.LmdbApi
import com.shalatan.entertainmentapp.utils.Constants
import kotlinx.coroutines.*

class PosterViewModel(val app: Application, val posterPath: String) : AndroidViewModel(app) {


    private val _posterURL = MutableLiveData<String>()
    val posterURL: LiveData<String>
        get() = _posterURL

    private var _posterSetAsWallpaperSnackbarEvent = MutableLiveData<Boolean>()
    private var _posterSavedSnackbarEvent = MutableLiveData<Boolean>()

    val posterSetAsWallpaperSnackbarEvent: LiveData<Boolean>
        get() = _posterSetAsWallpaperSnackbarEvent
    val posterSavedSnackbarEvent: LiveData<Boolean>
        get() = _posterSavedSnackbarEvent

    fun doneShowingSnackbar() {
        _posterSetAsWallpaperSnackbarEvent.value = false
        _posterSavedSnackbarEvent.value = false
    }


    init {
        _posterURL.value = posterPath
    }

    /**
     * set the position'th element of backdrops as wallpaper and trigger snackbar
     */
    fun setImageAsWallpaper(poster: String?) {
        Log.e("PVM1", _posterSetAsWallpaperSnackbarEvent.value.toString())
        val fullUrl = Constants.IMG_BASE_URL_O + poster
        Log.e("PVM1", fullUrl)
        Glide.with(app)
            .asBitmap()
            .load(fullUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    WallpaperManager.getInstance(app).setBitmap(resource)
                    _posterSetAsWallpaperSnackbarEvent.value = true
                    Log.e("PVM2", _posterSetAsWallpaperSnackbarEvent.value.toString())
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}