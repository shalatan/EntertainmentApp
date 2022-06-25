package com.shalatan.entertainmentapp.utils

import android.app.Activity
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2

class CustomViews {

    /**
     * function to make view pager view multiple items
     */
    fun setUpPosterViewPager(moviePosterViewPager: ViewPager2, activity: Activity) {
        with(moviePosterViewPager) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }
        val pageMarginPx = 20 * activity.resources.displayMetrics.density
        val offsetPx = 30 * activity.resources.displayMetrics.density
        moviePosterViewPager.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }
    }
}