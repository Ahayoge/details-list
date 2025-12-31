package com.example.booksapp.data.remote.api

import com.example.booksapp.data.remote.dto.BookDto
import com.example.booksapp.data.remote.dto.BooksResponseDto
import com.example.booksapp.data.remote.dto.GenresResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {
    @GET("api/books")
    suspend fun getBooks(
        @Query("genre") genre: String? = null,
        @Query("min_rating") minRating: String? = null,
        @Query("search") search: String? = null
    ): BooksResponseDto

    @GET("api/books/{id}")
    suspend fun getBookDetails(@Path("id") id: Int): BookDto

    @GET("api/genres")
    suspend fun getGenres(): GenresResponseDto
}