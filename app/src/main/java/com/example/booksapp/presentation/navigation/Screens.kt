package com.example.booksapp.presentation.navigation

sealed class Screens(val route: String) {
    object Books : Screens("books")
    object Favorites : Screens("favorites")
    object Filters : Screens("filters")
    object Profile : Screens("profile")
    object EditProfile : Screens("editProfile")

    object Details : Screens("details/{bookId}") {
        fun createRoute(bookId: Int) = "details/$bookId"
    }
}