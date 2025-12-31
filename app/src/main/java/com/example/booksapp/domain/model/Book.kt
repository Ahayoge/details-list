package com.example.booksapp.domain.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val genre: String,
    val year: Int,
    val rating: Double,
    val pages: Int,
    val description: String,
    val price: Int,
    val isFavorite: Boolean
)