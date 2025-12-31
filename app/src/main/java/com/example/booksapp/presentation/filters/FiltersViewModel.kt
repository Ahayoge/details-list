package com.example.booksapp.presentation.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.domain.usecase.GetGenresUseCase
import com.example.booksapp.presentation.common.FilterCache
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FiltersViewModel(
    private val getGenresUseCase: GetGenresUseCase,
    private val filterCache: FilterCache
) : ViewModel() {

    private val _state = MutableStateFlow(FiltersState())
    val state: StateFlow<FiltersState> = _state.asStateFlow()

    init {
        loadGenres()
        loadCurrentFilters()
    }

    private fun loadGenres() {
        viewModelScope.launch {
            try {
                val genres = getGenresUseCase()
                _state.value = _state.value.copy(genres = genres)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Failed to load genres"
                )
            }
        }
    }

    private fun loadCurrentFilters() {
        _state.value = _state.value.copy(
            selectedGenre = filterCache.currentGenre ?: "Все",
            minRating = filterCache.currentMinRating ?: 0f,
            searchQuery = filterCache.currentSearch ?: ""
        )
    }

    fun updateGenre(genre: String) {
        _state.value = _state.value.copy(selectedGenre = genre)
    }

    fun updateMinRating(rating: Float) {
        _state.value = _state.value.copy(minRating = rating)
    }

    fun updateSearchQuery(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
    }

    fun applyFilters() {
        filterCache.currentGenre = _state.value.selectedGenre.takeIf { it != "Все" }
        filterCache.currentMinRating = _state.value.minRating.takeIf { it > 0 }
        filterCache.currentSearch = _state.value.searchQuery.takeIf { it.isNotEmpty() }
    }

    fun clearFilters() {
        filterCache.clear()
        _state.value = _state.value.copy(
            selectedGenre = "Все",
            minRating = 0f,
            searchQuery = ""
        )
    }
}

data class FiltersState(
    val genres: List<String> = emptyList(),
    val selectedGenre: String = "Все",
    val minRating: Float = 0f,
    val searchQuery: String = "",
    val error: String? = null
)