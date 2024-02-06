package com.alex.yang.alextmdbnewscompose.movieList.domain.repository

import com.alex.yang.alextmdbnewscompose.movieList.domain.model.Movie
import com.alex.yang.alextmdbnewscompose.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by AlexYang on 2024/2/6.
 */
interface IMovieListRepository {

    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}