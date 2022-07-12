package com.shalatan.entertainmentapp.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.network.NetworkRepository
import com.shalatan.entertainmentapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: NetworkRepository) :
    ViewModel() {

    private val _searchedMovies = MutableLiveData<List<Movie>>()
    val searchedMovies: LiveData<List<Movie>>
        get() = _searchedMovies

    fun makeQuery(query: String) {
        viewModelScope.launch {
            val searchMovieDeferred = repository.getSearchedMovieAsync(Constants.API_KEY, query)
            try {
                _searchedMovies.value = searchMovieDeferred.await().movies
            } catch (t: Throwable) {
                Log.e("Error Fetching Complete Movie Detail : ", t.message.toString())
            }
        }
    }
}