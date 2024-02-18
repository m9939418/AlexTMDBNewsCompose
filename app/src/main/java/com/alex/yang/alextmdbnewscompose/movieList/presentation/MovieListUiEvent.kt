package com.alex.yang.alextmdbnewscompose.movieList.presentation

/**
 * Created by AlexYang on 2024/2/7.
 *
 *
 */
sealed interface MovieListUiEvent {
    data class Paginate(val category: String) : MovieListUiEvent
    object Navigate : MovieListUiEvent
}