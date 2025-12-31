package com.example.booksapp.presentation.common

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilterCache @Inject constructor() {
    var currentGenre: String? = null
    var currentMinRating: Float? = null
    var currentSearch: String? = null

    fun hasActiveFilters(): Boolean {
        return currentGenre != null || currentMinRating != null || currentSearch != null
    }

    fun clear() {
        currentGenre = null
        currentMinRating = null
        currentSearch = null
    }
}