package com.example.booksapp.data.repository

import android.util.Log
import com.example.booksapp.data.local.dao.FavoriteDao
import com.example.booksapp.data.local.entity.FavoriteBookEntity
import com.example.booksapp.data.remote.api.BooksApi
import com.example.booksapp.data.remote.dto.BookDto
import com.example.booksapp.domain.model.Book
import com.example.booksapp.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val api: BooksApi,
    private val favoriteDao: FavoriteDao
) : BooksRepository {

    override suspend fun getBooks(
        genre: String?,
        minRating: Float?,
        search: String?
    ): List<Book> {
        Log.d("BooksRepository", "getBooks called with: genre=$genre, minRating=$minRating, search=$search")

        try {
            Log.d("BooksRepository", "Making API call...")

            val response = api.getBooks(
                genre = genre?.takeIf { it.isNotEmpty() && it != "Все" },
                minRating = minRating?.toString(),
                search = search?.takeIf { it.isNotEmpty() }
            )

            Log.d("BooksRepository", "API Response received: total=${response.total}, books=${response.books.size}")
            Log.d("BooksRepository", "First book: ${response.books.firstOrNull()?.title}")

            response.books.forEachIndexed { index, book ->
                Log.d("BooksRepository", "Book $index: id=${book.id}, title=${book.title}, author=${book.author}")
            }

            return response.books.map { dto ->
                val isFavorite = favoriteDao.isFavorite(dto.id) > 0
                Log.d("BooksRepository", "Book ${dto.id} isFavorite=$isFavorite")
                dto.toDomain(isFavorite)
            }

        } catch (e: Exception) {
            Log.e("BooksRepository", "Error getting books", e)
            Log.e("BooksRepository", "Error message: ${e.message}")
            Log.e("BooksRepository", "Error cause: ${e.cause}")
            throw e
        }
    }

    override suspend fun getBookDetails(id: Int): Book {
        Log.d("BooksRepository", "getBookDetails called with id=$id")

        try {
            val dto = api.getBookDetails(id)
            Log.d("BooksRepository", "Book details received: ${dto.title}")
            Log.d("BooksRepository", "Full book data: $dto")

            val isFavorite = favoriteDao.isFavorite(id) > 0
            return dto.toDomain(isFavorite)

        } catch (e: Exception) {
            Log.e("BooksRepository", "Error getting book details", e)
            throw e
        }
    }

    override suspend fun getGenres(): List<String> {
        Log.d("BooksRepository", "getGenres called")

        try {
            val response = api.getGenres()
            Log.d("BooksRepository", "Genres received: ${response.genres}")
            return response.genres

        } catch (e: Exception) {
            Log.e("BooksRepository", "Error getting genres", e)
            throw e
        }
    }

    override fun getFavorites(): Flow<List<Book>> {
        return favoriteDao.getAllFavorites().map { entities ->
            entities.map { it.toDomain(true) }
        }
    }

    override suspend fun addToFavorites(book: Book) {
        favoriteDao.addFavorite(book.toEntity())
    }

    override suspend fun removeFromFavorites(bookId: Int) {
        favoriteDao.removeFavoriteById(bookId)
    }

    override suspend fun isFavorite(bookId: Int): Boolean {
        return favoriteDao.isFavorite(bookId) > 0
    }

    private fun BookDto.toDomain(isFavorite: Boolean): Book {
        return Book(
            id = id,
            title = title,
            author = author,
            genre = genre,
            year = year,
            rating = rating,
            pages = pages,
            description = description,
            price = price,
            isFavorite = isFavorite
        )
    }

    private fun Book.toEntity(): FavoriteBookEntity {
        return FavoriteBookEntity(
            id = id,
            title = title,
            author = author,
            genre = genre,
            year = year,
            rating = rating,
            pages = pages,
            description = description,
            price = price
        )
    }

    private fun FavoriteBookEntity.toDomain(isFavorite: Boolean): Book {
        return Book(
            id = id,
            title = title,
            author = author,
            genre = genre,
            year = year,
            rating = rating,
            pages = pages,
            description = description,
            price = price,
            isFavorite = isFavorite
        )
    }
}