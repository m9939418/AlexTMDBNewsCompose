package com.alex.yang.alextmdbnewscompose.movieList.data.datasource.remote.response

/**
 * Created by AlexYang on 2024/2/6.
 */
data class MovieListDTO(
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)