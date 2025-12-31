package com.example.booksapp.domain.usecase

import com.example.booksapp.domain.model.Book
import com.example.booksapp.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow

class GetBooksUseCase(
    private val repository: BooksRepository
) {
    suspend operator fun invoke(
        genre: String? = null,
        minRating: Float? = null,
        search: String? = null
    ): List<Book> {
        return repository.getBooks(genre, minRating, search)
    }
}

class ToggleFavoriteUseCase(
    private val repository: BooksRepository
) {
    suspend operator fun invoke(book: Book) {
        if (book.isFavorite) {
            repository.removeFromFavorites(book.id)
        } else {
            repository.addToFavorites(book)
        }
    }
}

class GetGenresUseCase(
    private val repository: BooksRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getGenres()
    }
}

class GetFavoritesUseCase(
    private val repository: BooksRepository
) {
    operator fun invoke(): Flow<List<Book>> {
        return repository.getFavorites()
    }
}