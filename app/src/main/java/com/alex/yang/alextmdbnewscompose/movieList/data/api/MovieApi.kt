package com.alex.yang.alextmdbnewscompose.movieList.data.api

import com.alex.yang.alextmdbnewscompose.movieList.data.datasource.remote.response.MovieListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") api_key: String = API_KEY,
    ): MovieListDTO


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "8b00637dc28d1aacc02b261705775392"
    }
}