package com.example.booksapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.example.booksapp.data.local.entity.FavoriteBookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_books")
    fun getAllFavorites(): Flow<List<FavoriteBookEntity>>

    @Query("SELECT * FROM favorite_books WHERE id = :bookId")
    suspend fun getFavorite(bookId: Int): FavoriteBookEntity?

    @Insert
    suspend fun addFavorite(book: FavoriteBookEntity)

    @Delete
    suspend fun removeFavorite(book: FavoriteBookEntity)

    @Query("DELETE FROM favorite_books WHERE id = :bookId")
    suspend fun removeFavoriteById(bookId: Int)

    @Query("SELECT COUNT(*) FROM favorite_books WHERE id = :bookId")
    suspend fun isFavorite(bookId: Int): Int
}