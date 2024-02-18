@file:Suppress("SpellCheckingInspection")

package com.alex.yang.alextmdbnewscompose.di

import android.app.Application
import androidx.room.Room
import com.alex.yang.alextmdbnewscompose.movieList.data.api.MovieApi
import com.alex.yang.alextmdbnewscompose.movieList.data.datasource.local.movie.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by AlexYang on 2024/2/7.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesMovieApi(): MovieApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MovieApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieApi::class.java)

    @Provides
    @Singleton
    fun providesMovieDatabase(app: Application): MovieDatabase =
        Room.databaseBuilder(
            context = app,
            klass = MovieDatabase::class.java,
            name = "moviedb.db"
        ).build()
}


