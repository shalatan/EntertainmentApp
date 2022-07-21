package com.shalatan.entertainmentapp.utils

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

class Extension {

    fun View.showSnackBar(
        message: String,
        length: Int = Snackbar.LENGTH_LONG,
        actionMsg: String? = null,
        action: (() -> Unit)? = null
    ) {
        Snackbar.make(this, message, length).apply {
            actionMsg?.let {
                setAction(actionMsg) {
                    action?.invoke()
                }
            }
            show()
        }
    }

    fun Fragment.showSnackBar(
        message: String,
        length: Int = Snackbar.LENGTH_LONG,
        actionMsg: String? = null,
        action: (() -> Unit)? = null
    ) = requireView().showSnackBar(
        message = message,
        action = action,
        actionMsg = actionMsg,
        length = length
    )
}