package com.shalatan.entertainmentapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.shalatan.entertainmentapp.database.MovieDAO
import com.shalatan.entertainmentapp.database.MovieDatabase
import com.shalatan.entertainmentapp.model.Movie
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

//@RunWith(AndroidJUnit4::class)
//class SleepDatabaseTest {
//
//    private lateinit var movieDAO: MovieDAO
//    private lateinit var database: MovieDatabase
//
//    @Before
//    fun createDb() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        // Using an in-memory database because the information stored here disappears when the
//        // process is killed.
//        database = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
//            // Allowing main thread queries, just for testing.
//            .allowMainThreadQueries()
//            .build()
//        movieDAO = database.movieDao()
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        database.close()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun insertAndGetNight() {
//        val movie = Movie()
//        movieDAO.insert(movie)
//        val tonight = movieDAO.getAllMovies()
//        assertEquals(tonight?.vote_count, -1)
//    }
//}