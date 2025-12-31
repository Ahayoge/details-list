package com.example.booksapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.booksapp.domain.model.Profile
import com.example.booksapp.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile_store")

class ProfileRepositoryImpl @Inject constructor(
    private val context: Context
) : ProfileRepository {

    companion object {
        private val FULL_NAME_KEY = stringPreferencesKey("full_name")
        private val AVATAR_URI_KEY = stringPreferencesKey("avatar_uri")
        private val RESUME_URL_KEY = stringPreferencesKey("resume_url")
        private val POSITION_KEY = stringPreferencesKey("position")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PHONE_KEY = stringPreferencesKey("phone")
    }

    override suspend fun getProfile(): Profile {
        val preferences = context.dataStore.data.first()
        return Profile(
            fullName = preferences[FULL_NAME_KEY] ?: "",
            avatarUri = preferences[AVATAR_URI_KEY],
            resumeUrl = preferences[RESUME_URL_KEY] ?: "",
            position = preferences[POSITION_KEY] ?: "",
            email = preferences[EMAIL_KEY] ?: "",
            phone = preferences[PHONE_KEY] ?: ""
        )
    }

    override fun getProfileFlow(): Flow<Profile> {
        return context.dataStore.data.map { preferences ->
            Profile(
                fullName = preferences[FULL_NAME_KEY] ?: "",
                avatarUri = preferences[AVATAR_URI_KEY],
                resumeUrl = preferences[RESUME_URL_KEY] ?: "",
                position = preferences[POSITION_KEY] ?: "",
                email = preferences[EMAIL_KEY] ?: "",
                phone = preferences[PHONE_KEY] ?: ""
            )
        }
    }

    override suspend fun saveProfile(profile: Profile) {
        context.dataStore.edit { preferences ->
            preferences[FULL_NAME_KEY] = profile.fullName
            profile.avatarUri?.let { preferences[AVATAR_URI_KEY] = it }
            preferences[RESUME_URL_KEY] = profile.resumeUrl
            preferences[POSITION_KEY] = profile.position
            preferences[EMAIL_KEY] = profile.email
            preferences[PHONE_KEY] = profile.phone
        }
    }
}