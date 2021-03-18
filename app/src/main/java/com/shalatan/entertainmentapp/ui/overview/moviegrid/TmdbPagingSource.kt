package com.shalatan.entertainmentapp.ui.overview.moviegrid
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.shalatan.entertainmentapp.model.Movie
//import com.shalatan.entertainmentapp.model.MovieResponse
//import com.shalatan.entertainmentapp.network.TmdbApiService
//import com.shalatan.entertainmentapp.utils.Constants
//import retrofit2.HttpException
//import java.io.IOException
//
//private const val STARTING_PAGE_INDEX = 1
//
//class TmdbPagingSource(private val tmdbApiService: TmdbApiService) : PagingSource<Int, Movie>(){
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
//        val position = params.key ?: STARTING_PAGE_INDEX
//        return try{
//            val response = tmdbApiService.getNowPlayingMoviesAsync(position)
//            val movies = response.await().movies
//            val nextKey = if (movies.isEmpty()){
//                null
//            }else{
//                position + (params.loadSize/50)
//            }
//            LoadResult.Page(
//                movies,
//                prevKey = if(position == STARTING_PAGE_INDEX) null else position-1,
//                nextKey
//            )
//        }catch (exception: IOException) {
//            return LoadResult.Error(exception)
//        }catch (exception: HttpException){
//            return LoadResult.Error(exception)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
//        TODO("Not yet implemented")
//    }
//}