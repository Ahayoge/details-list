package com.example.booksapp.domain.repository

import com.example.booksapp.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(): Profile
    fun getProfileFlow(): Flow<Profile>
    suspend fun saveProfile(profile: Profile)
}