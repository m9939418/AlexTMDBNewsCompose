package com.alex.yang.alextmdbnewscompose.movieList.data.datasource.local.movie

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by AlexYang on 2024/2/6.
 */
@Database(
    entities = [MovieEntity::class],
    version = 1,
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}