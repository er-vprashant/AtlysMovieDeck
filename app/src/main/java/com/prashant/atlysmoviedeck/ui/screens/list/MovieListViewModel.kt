package com.prashant.atlysmoviedeck.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashant.atlysmoviedeck.data.repository.MovieRepository
import com.prashant.atlysmoviedeck.domain.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MovieListUiState(
    val query: String = "",
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val isLoading = MutableStateFlow(false)
    private val errorMessage = MutableStateFlow<String?>(null)

    private val movies: StateFlow<List<Movie>> = query
        .flatMapLatest { q -> repository.observeTrendingMovies(searchQuery = q) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val uiState: StateFlow<MovieListUiState> = combine(
        query,
        movies,
        isLoading,
        errorMessage
    ) { q, m, loading, error ->
        MovieListUiState(
            query = q,
            movies = m,
            isLoading = loading,
            errorMessage = error
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MovieListUiState())

    init {
        refresh()
    }

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }

    fun refresh() {
        viewModelScope.launch {
            errorMessage.value = null
            isLoading.value = true
            val result = repository.refreshTrendingMovies()
            if (result.isFailure) {
                errorMessage.value = toUserMessage(result.exceptionOrNull())
            }
            isLoading.value = false
        }
    }

    private fun toUserMessage(t: Throwable?): String {
        return when (t) {
            is UnknownHostException -> "No internet connection (DNS failure). Please check your network."
            is SocketTimeoutException -> "Request timed out. Please try again."
            else -> t?.message ?: "Failed to refresh"
        }
    }
}
