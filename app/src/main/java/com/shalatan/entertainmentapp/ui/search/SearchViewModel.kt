package com.shalatan.entertainmentapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalatan.entertainmentapp.MyApplication
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: NetworkRepository) :
    ViewModel() {

    private companion object {
        const val LOG = MyApplication.LOG
    }

    private val _searchedMoviesFlow = MutableStateFlow<List<Movie>>(emptyList())
    val searchedMoviesFlow: StateFlow<List<Movie>> = _searchedMoviesFlow

    fun makeQuery(query: String) {
        viewModelScope.launch {
            repository.getSearchedMovieAsync(query = query)
                .flowOn(Dispatchers.IO)
                .catch {
                    Timber.d("$LOG exception: $it")
                }
                .collect {
                    _searchedMoviesFlow.value = it.movies
                }
        }
    }
}