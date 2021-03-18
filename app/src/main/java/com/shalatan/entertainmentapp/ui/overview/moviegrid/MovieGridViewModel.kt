package com.shalatan.entertainmentapp.ui.overview.moviegrid

//import android.util.Log
//import androidx.lifecycle.*
//import androidx.paging.cachedIn
//import com.shalatan.entertainmentapp.model.Movie
//import com.shalatan.entertainmentapp.network.TmdbApi
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.launch
//
//class MovieGridViewModel(private val repository: MovieGridRepository) : ViewModel() {
//
//    private val currentQuery = MutableLiveData(DEFAULT)
//
//    val movies = currentQuery.switchMap {
//        repository.getSearchResults().cachedIn(viewModelScope)
//    }
//
//    fun searchMovies(){
//        Log.e("VM Search Movies :",currentQuery.value.toString())
//    }
//
//    companion object{
//        const val DEFAULT = "https://api.themoviedb.org/3/movie/popular?api_key=ea9a49ebf2b74721a75aae271ebd3036"
//    }
//}