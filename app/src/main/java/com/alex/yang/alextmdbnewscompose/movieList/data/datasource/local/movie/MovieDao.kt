package com.alex.yang.alextmdbnewscompose.movieList.data.datasource.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

/**
 * Created by AlexYang on 2024/2/6.
 */
@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovieList(movieList: List<MovieEntity>)

    @Query("select * from MovieEntity where id= :id")
    suspend fun getMovieById(id: Int): MovieEntity

    @Query("select * from MovieEntity where category= :category")
    suspend fun getMovieByCategory(category: String): List<MovieEntity>
}