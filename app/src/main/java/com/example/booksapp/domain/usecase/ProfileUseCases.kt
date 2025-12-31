package com.example.booksapp.domain.usecase

import com.example.booksapp.domain.model.Profile
import com.example.booksapp.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Profile {
        return repository.getProfile()
    }

    fun getProfileFlow(): Flow<Profile> {
        return repository.getProfileFlow()
    }
}

class SaveProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile) {
        repository.saveProfile(profile)
    }
}