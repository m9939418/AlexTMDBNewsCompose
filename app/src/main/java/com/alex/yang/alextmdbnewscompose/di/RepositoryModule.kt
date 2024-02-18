package com.alex.yang.alextmdbnewscompose.di

import com.alex.yang.alextmdbnewscompose.movieList.data.respository.MovieListRepositoryImpl
import com.alex.yang.alextmdbnewscompose.movieList.domain.repository.IMovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by AlexYang on 2024/2/7.
 *
 *
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsMovieListRepository(
        movieListRepositoryImpl: MovieListRepositoryImpl
    ): IMovieListRepository
}