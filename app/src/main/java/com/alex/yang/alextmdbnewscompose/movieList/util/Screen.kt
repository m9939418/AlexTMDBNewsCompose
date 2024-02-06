package com.alex.yang.alextmdbnewscompose.movieList.util

/**
 * Created by AlexYang on 2024/2/6.
 */
sealed class Screen(val rout: String) {
    object Home : Screen("main")
    object PopularMovieList : Screen("popularMovie")
    object UpcomingMovieList : Screen("upcomingMovie")
    object Details : Screen("details")
}
