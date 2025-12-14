package com.prashant.atlysmoviedeck.ui.screens.list

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashant.atlysmoviedeck.data.repository.MovieRepository
import com.prashant.atlysmoviedeck.domain.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class MovieListUiState(
    val query: String = ""
)

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val query = MutableStateFlow("")

    val uiState: StateFlow<MovieListUiState> = query
        .map { q -> MovieListUiState(query = q) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MovieListUiState())

    val movies: kotlinx.coroutines.flow.Flow<PagingData<Movie>> = query
        .flatMapLatest { q -> repository.observeTrendingMoviesPaged(searchQuery = q) }
        .cachedIn(viewModelScope)

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }
}
