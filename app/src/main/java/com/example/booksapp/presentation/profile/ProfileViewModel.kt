package com.example.booksapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.domain.model.Profile
import com.example.booksapp.domain.usecase.GetProfileUseCase
import com.example.booksapp.domain.usecase.SaveProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    val profileFlow: StateFlow<Profile> = getProfileUseCase.getProfileFlow()
        .map { profile ->
            profile
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Profile()
        )

    init {
        loadProfile()
        viewModelScope.launch {
            profileFlow.collect { profile ->
                _state.value = _state.value.copy(
                    profile = profile,
                    isLoading = false
                )
            }
        }
    }

    fun loadProfile() {
        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val profile = getProfileUseCase()
                _state.value = _state.value.copy(
                    profile = profile,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Ошибка загрузки профиля: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun updateProfile(profile: Profile) {
        viewModelScope.launch {
            try {
                saveProfileUseCase(profile)
                _state.value = _state.value.copy(
                    error = null,
                    profile = profile
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Ошибка сохранения профиля: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}

data class ProfileState(
    val profile: Profile = Profile(),
    val isLoading: Boolean = true,
    val error: String? = null
)