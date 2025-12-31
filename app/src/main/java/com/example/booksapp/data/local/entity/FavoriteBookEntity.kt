package com.example.booksapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class FavoriteBookEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val author: String,
    val genre: String,
    val year: Int,
    val rating: Double,
    val pages: Int,
    val description: String,
    val price: Int,
    val addedAt: Long = System.currentTimeMillis()
)