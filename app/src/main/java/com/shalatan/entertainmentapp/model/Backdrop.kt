package com.shalatan.entertainmentapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Backdrop(
    val file_path: String
) : Parcelable