package com.alex.yang.alextmdbnewscompose.details.presentaion

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.yang.alextmdbnewscompose.movieList.domain.repository.IMovieListRepository
import com.alex.yang.alextmdbnewscompose.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by AlexYang on 2024/2/22.
 *
 *
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieListRepository: IMovieListRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>("movieId")

    private var _detailsState = MutableStateFlow(DetailState())
    val detailsState = _detailsState.asStateFlow()

    init {
        getMovie(movieId ?: -1)
    }

    private fun getMovie(id: Int) {
        viewModelScope.launch {
            _detailsState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMovie(id).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _detailsState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        _detailsState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { movie ->
                            _detailsState.update {
                                it.copy(movie = movie)
                            }
                        }
                    }
                }
            }
        }
    }
}