package com.shalatan.entertainmentapp.ui.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OverviewViewModel : ViewModel(){


    // The internal MutableLiveData String that stores the most recent response status
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the status String
    val response: LiveData<String>
        get() = _response

    init {
        Log.d("OverviewViewModel : ","Created")
        getJSON()
    }

    private fun getJSON() {
        TODO("Not yet implemented")
    }
}