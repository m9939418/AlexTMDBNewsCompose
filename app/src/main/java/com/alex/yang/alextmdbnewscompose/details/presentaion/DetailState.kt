package com.alex.yang.alextmdbnewscompose.details.presentaion

import com.alex.yang.alextmdbnewscompose.movieList.domain.model.Movie

/**
 * Created by AlexYang on 2024/2/20.
 *
 *
 */
data class DetailState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
