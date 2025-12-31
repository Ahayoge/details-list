package com.example.booksapp.presentation.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.domain.model.Book
import com.example.booksapp.domain.usecase.GetBooksUseCase
import com.example.booksapp.domain.usecase.ToggleFavoriteUseCase
import com.example.booksapp.presentation.common.FilterCache
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BooksViewModel(
    private val getBooksUseCase: GetBooksUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val filterCache: FilterCache
) : ViewModel() {

    private val _state = MutableStateFlow(BooksState())
    val state: StateFlow<BooksState> = _state.asStateFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val books = getBooksUseCase(
                    genre = filterCache.currentGenre,
                    minRating = filterCache.currentMinRating,
                    search = filterCache.currentSearch
                )
                _state.value = _state.value.copy(
                    books = books,
                    isLoading = false,
                    hasFilters = filterCache.hasActiveFilters()
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Unknown error",
                    isLoading = false
                )
            }
        }
    }

    fun toggleFavorite(book: Book) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(book)
                loadBooks()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Failed to toggle favorite"
                )
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}

data class BooksState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val hasFilters: Boolean = false
)