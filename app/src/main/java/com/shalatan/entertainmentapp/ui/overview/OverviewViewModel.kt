package com.shalatan.entertainmentapp.ui.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shalatan.entertainmentapp.network.LmdbApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OverviewViewModel : ViewModel() {


    // The internal MutableLiveData String that stores the most recent response status
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the status String
    val response: LiveData<String>
        get() = _response

    init {
        Log.d("OverviewViewModel : ", "Created")
        fetchJSON()
    }

    private fun fetchJSON() {
        LmdbApi.retrofitService.getProperties().enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                _response.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failure" + t.message

            }
        })
    }
}