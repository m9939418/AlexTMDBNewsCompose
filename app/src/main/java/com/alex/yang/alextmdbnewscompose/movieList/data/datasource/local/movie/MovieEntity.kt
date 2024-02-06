package com.alex.yang.alextmdbnewscompose.movieList.data.datasource.local.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by AlexYang on 2024/2/6.
 */
@Entity
data class MovieEntity(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,

    @PrimaryKey
    val id: Int,
    val category: String,
)