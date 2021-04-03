package com.shalatan.entertainmentapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.shalatan.entertainmentapp.model.Movie

//videoDao - CachedMovieDao
//DatabaseVideo - CachedMovie
//VideosDatabase - CachedDatabase
//"videos" - "cachedMovies"

//@Dao
//interface CachedMovieDao {
//
//    @Query("select * from cachedmovie")
//    fun getMovies(): LiveData<List<CachedMovie>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAllCachedMovie(vararg cachedMovies: CachedMovie)
//}
//
//@Database(entities = [CachedMovie::class], version = 1)
//abstract class CachedDatabase: RoomDatabase(){
//    abstract val cachedMovieDao: CachedMovieDao
//}
//
//private lateinit var INSTANCE: CachedDatabase
//fun getDatabase(context: Context): CachedDatabase{
//    synchronized(CachedDatabase::class.java){
//        if(!::INSTANCE.isInitialized){
//            INSTANCE = Room.databaseBuilder(context.applicationContext,
//            CachedDatabase::class.java,"cachesMovies")
//                .build()
//        }
//    }
//    return INSTANCE
//}
