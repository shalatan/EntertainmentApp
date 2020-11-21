package com.shalatan.entertainmentapp.database

//@Database(entities = [MovieResult::class], version = 1)
//abstract class MovieDatabase : RoomDatabase() {
//
//    abstract fun getMovieDao(): MovieDAO
//
//    companion object {
//        @Volatile
//        private var INSTANCE: MovieDatabase? = null
//
//        private val LOCK = Any()
//
//        fun getInstance(context: Context): MovieDatabase {
//
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                            context.applicationContext,
//                            MovieDatabase::class.java,
//                            "sleep_history_database"
//                    ).fallbackToDestructiveMigration()
//                            .build()
//
//                    INSTANCE = instance
//                }
//
//                return instance
//            }
//        }
//    }
//}