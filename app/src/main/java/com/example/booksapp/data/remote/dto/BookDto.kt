package com.example.booksapp.data.remote.dto

import com.squareup.moshi.Json

data class BookDto(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "author") val author: String,
    @Json(name = "genre") val genre: String,
    @Json(name = "year") val year: Int,
    @Json(name = "rating") val rating: Double,
    @Json(name = "pages") val pages: Int,
    @Json(name = "description") val description: String,
    @Json(name = "price") val price: Int,
    @Json(name = "isFavorite") val isFavorite: Boolean = false
)

data class BooksResponseDto(
    @Json(name = "books") val books: List<BookDto>,
    @Json(name = "total") val total: Int,
    @Json(name = "timestamp") val timestamp: String
)

data class GenresResponseDto(
    @Json(name = "genres") val genres: List<String>
)