package com.alex.yang.alextmdbnewscompose.movieList.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alex.yang.alextmdbnewscompose.movieList.presentation.components.MovieItem
import com.alex.yang.alextmdbnewscompose.movieList.util.Category

/**
 * Created by AlexYang on 2024/2/18.
 *
 *
 */
@Composable
fun PopularMovieScreen(
    navHostController: NavHostController,
    movieListState: MovieListState,
    onEvent: (MovieListUiEvent) -> Unit,
) {
    if (movieListState.popularMovieList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(movieListState.popularMovieList.size) { index: Int ->
                MovieItem(
                    movie = movieListState.popularMovieList[index],
                    navHostController = navHostController
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (index >= movieListState.popularMovieList.size - 1 && !movieListState.isLoading) {
                    onEvent(MovieListUiEvent.Paginate(Category.POPULAR))
                }
            }
        }
    }
}