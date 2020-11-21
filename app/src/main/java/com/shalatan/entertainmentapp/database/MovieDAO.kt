package com.shalatan.entertainmentapp.database

//@Dao
//interface MovieDAO {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun upsert(movieResult: MovieResult): Long
//
//    @Query("SELECT * FROM movies")
//    fun getAllMovies(): LiveData<List<MovieResult>>
//
//    @Delete
//    suspend fun deleteMovie(movieResult: MovieResult)
//}