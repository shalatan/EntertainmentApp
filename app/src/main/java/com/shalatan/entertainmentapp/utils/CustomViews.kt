package com.shalatan.entertainmentapp.utils

import android.app.Activity
import android.view.Window

import android.view.WindowManager

class CustomViews {

    fun changeStatusBarColor(color: Int, activity: Activity) {
        val window: Window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }
}