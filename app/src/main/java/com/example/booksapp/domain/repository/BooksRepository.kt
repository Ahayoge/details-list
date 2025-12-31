package com.example.booksapp.domain.repository

import com.example.booksapp.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun getBooks(
        genre: String? = null,
        minRating: Float? = null,
        search: String? = null
    ): List<Book>

    suspend fun getBookDetails(id: Int): Book
    suspend fun getGenres(): List<String>
    fun getFavorites(): Flow<List<Book>>
    suspend fun addToFavorites(book: Book)
    suspend fun removeFromFavorites(bookId: Int)
    suspend fun isFavorite(bookId: Int): Boolean
}