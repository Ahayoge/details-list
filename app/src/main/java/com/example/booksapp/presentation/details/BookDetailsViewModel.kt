package com.example.booksapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.domain.model.Book
import com.example.booksapp.domain.repository.BooksRepository
import com.example.booksapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookDetailsViewModel @Inject constructor(
    private val repository: BooksRepository,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailsState())
    val state: StateFlow<BookDetailsState> = _state.asStateFlow()

    private var currentBookId: Int = -1

    fun loadBook(bookId: Int) {
        if (bookId != currentBookId) {
            currentBookId = bookId
            loadBookDetails()
        }
    }

    private fun loadBookDetails() {
        if (currentBookId == -1) return

        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val book = repository.getBookDetails(currentBookId)
                _state.value = _state.value.copy(
                    book = book,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Unknown error",
                    isLoading = false
                )
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentBook = _state.value.book ?: return@launch
            try {
                toggleFavoriteUseCase(currentBook)
                val updatedBook = currentBook.copy(isFavorite = !currentBook.isFavorite)
                _state.value = _state.value.copy(book = updatedBook)
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

    fun reload() {
        loadBookDetails()
    }
}

data class BookDetailsState(
    val book: Book? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)