package com.shalatan.entertainmentapp.ui.overview.moviegrid
//
//import androidx.lifecycle.LiveData
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.PagingData
//import androidx.paging.liveData
//import com.shalatan.entertainmentapp.model.Movie
//import com.shalatan.entertainmentapp.network.TmdbApiService
//
//class MovieGridRepository(private val tmdbApiService: TmdbApiService) {
//
//    fun getSearchResults(): LiveData<PagingData<Movie>>{
//        return Pager(
//            config = PagingConfig(
//                pageSize = NETWORK_PAGE_SIZE,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = {
//                TmdbPagingSource(tmdbApiService)
//            }
//        ).liveData
//    }
//
//    companion object {
//        private const val NETWORK_PAGE_SIZE = 50
//    }
//}