package com.alex.yang.alextmdbnewscompose.movieList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.yang.alextmdbnewscompose.movieList.domain.repository.IMovieListRepository
import com.alex.yang.alextmdbnewscompose.movieList.util.Category
import com.alex.yang.alextmdbnewscompose.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by AlexYang on 2024/2/7.
 *
 *
 */
@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: IMovieListRepository
) : ViewModel() {
    private val _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            MovieListUiEvent.Navigate -> {
                _movieListState.update {
                    it.copy(
                        isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen
                    )
                }
            }

            is MovieListUiEvent.Paginate -> {
                when (event.category) {
                    Category.POPULAR -> getPopularMovieList(true)
                    Category.UPCOMING -> getUpcomingMovieList(true)
                }
            }
        }
    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMovieList(
                forceFetchFromRemote = forceFetchFromRemote,
                category = Category.POPULAR,
                page = _movieListState.value.popularMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let {  popularList->
                            _movieListState.update {
                                it.copy(
                                    popularMovieList = movieListState.value.popularMovieList
                                            + popularList.shuffled(),
                                    popularMovieListPage = movieListState.value.popularMovieListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMovieList(
                forceFetchFromRemote = forceFetchFromRemote,
                category = Category.UPCOMING,
                page = _movieListState.value.upcomingMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { upcomingList ->
                            _movieListState.update {
                                it.copy(
                                    upcomingMovieList = movieListState.value.upcomingMovieList
                                            + upcomingList.shuffled(),
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }
}