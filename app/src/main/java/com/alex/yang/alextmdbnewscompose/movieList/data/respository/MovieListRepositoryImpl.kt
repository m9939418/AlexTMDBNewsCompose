package com.alex.yang.alextmdbnewscompose.movieList.data.respository

import com.alex.yang.alextmdbnewscompose.movieList.data.api.MovieApi
import com.alex.yang.alextmdbnewscompose.movieList.data.datasource.local.movie.MovieDatabase
import com.alex.yang.alextmdbnewscompose.movieList.data.mapper.toMovie
import com.alex.yang.alextmdbnewscompose.movieList.data.mapper.toMovieEntity
import com.alex.yang.alextmdbnewscompose.movieList.domain.model.Movie
import com.alex.yang.alextmdbnewscompose.movieList.domain.repository.IMovieListRepository
import com.alex.yang.alextmdbnewscompose.movieList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by AlexYang on 2024/2/6.
 */
class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase,
) : IMovieListRepository {

    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            /** 取得 Local MovieList **/
            val localMovieList = movieDatabase.movieDao.getMovieByCategory(category = category)
            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote
            if (shouldLoadLocalMovie) {
                emit(
                    Resource.Success(
                        data = localMovieList.map { entity ->
                            entity.toMovie(category)
                        }
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            /** 取得 Remote MovieList **/
            val movieListFromApi = try {
                movieApi.getMovieList(category = category, page = page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            /** 將 MovieList 儲存 Local Database **/
            val movieEntities = movieListFromApi.results.let { list ->
                list.map { movieDTO -> movieDTO.toMovieEntity(category = category) }
            }
            emit(Resource.Success(
                data = movieEntities.map { movieEntity ->
                    movieEntity.toMovie(category)
                }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.getMovieById(id = id)
            if (movieEntity != null) {
                emit(
                    Resource.Success(
                        data = movieEntity.toMovie(category = movieEntity.category)
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }
            emit(Resource.Error(message = "Error no such movie"))
            emit(Resource.Loading(false))
        }
    }
}