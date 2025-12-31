package com.example.booksapp.domain.model

data class Profile(
    val id: Int = 1,
    val fullName: String = "",
    val avatarUri: String? = null,
    val resumeUrl: String = "",
    val position: String = "",
    val email: String = "",
    val phone: String = ""
)