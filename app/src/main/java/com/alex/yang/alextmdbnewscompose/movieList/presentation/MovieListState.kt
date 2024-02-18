package com.alex.yang.alextmdbnewscompose.movieList.presentation

import com.alex.yang.alextmdbnewscompose.movieList.domain.model.Movie

/**
 * Created by AlexYang on 2024/2/7.
 *
 *
 */
data class MovieListState(
    val isLoading: Boolean = false,

    val popularMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,

    val isCurrentPopularScreen: Boolean = true,

    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList(),
)
