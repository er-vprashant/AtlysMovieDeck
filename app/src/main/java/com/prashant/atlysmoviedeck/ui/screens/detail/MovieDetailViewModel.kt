package com.prashant.atlysmoviedeck.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashant.atlysmoviedeck.data.repository.MovieRepository
import com.prashant.atlysmoviedeck.domain.Movie
import com.prashant.atlysmoviedeck.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class MovieDetailUiState(
    val movieId: Long,
    val movie: Movie?
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: MovieRepository
) : ViewModel() {

    private val movieId: Long = savedStateHandle.get<Long>(Routes.MovieIdArg)
        ?: error("Missing navigation argument: ${Routes.MovieIdArg}")

    val uiState: StateFlow<MovieDetailUiState> = repository
        .observeMovie(movieId)
        .map { movie -> MovieDetailUiState(movieId = movieId, movie = movie) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MovieDetailUiState(movieId = movieId, movie = null)
        )
}
