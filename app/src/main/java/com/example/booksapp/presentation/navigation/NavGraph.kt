package com.example.booksapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.booksapp.presentation.books.BooksScreen
import com.example.booksapp.presentation.details.BookDetailsScreen
import com.example.booksapp.presentation.favorites.FavoritesScreen
import com.example.booksapp.presentation.filters.FiltersScreen
import com.example.booksapp.presentation.profile.EditProfileScreen
import com.example.booksapp.presentation.profile.ProfileScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Books.route,
        modifier = modifier
    ) {
        composable(Screens.Books.route) {
            val viewModel = koinViewModel<com.example.booksapp.presentation.books.BooksViewModel>()
            BooksScreen(
                viewModel = viewModel,
                onBookClick = { bookId ->
                    navController.navigate(Screens.Details.createRoute(bookId))
                },
                onFiltersClick = {
                    navController.navigate(Screens.Filters.route)
                }
            )
        }

        composable(Screens.Favorites.route) {
            val viewModel = koinViewModel<com.example.booksapp.presentation.favorites.FavoritesViewModel>()
            FavoritesScreen(
                viewModel = viewModel,
                onBookClick = { bookId ->
                    navController.navigate(Screens.Details.createRoute(bookId))
                }
            )
        }

        composable(Screens.Filters.route) {
            val viewModel = koinViewModel<com.example.booksapp.presentation.filters.FiltersViewModel>()
            FiltersScreen(
                viewModel = viewModel,
                onApplyFilters = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screens.Profile.route) {
            val viewModel = koinViewModel<com.example.booksapp.presentation.profile.ProfileViewModel>()
            ProfileScreen(
                viewModel = viewModel,
                onEditClick = {
                    navController.navigate(Screens.EditProfile.route)
                }
            )
        }

        composable(Screens.EditProfile.route) {
            val viewModel = koinViewModel<com.example.booksapp.presentation.profile.ProfileViewModel>()
            EditProfileScreen(
                viewModel = viewModel,
                onSaveClick = {
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screens.Details.route,
            arguments = listOf(
                androidx.navigation.navArgument("bookId") {
                    type = androidx.navigation.NavType.IntType
                }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
            val viewModel = koinViewModel<com.example.booksapp.presentation.details.BookDetailsViewModel>()

            androidx.compose.runtime.LaunchedEffect(bookId) {
                viewModel.loadBook(bookId)
            }

            BookDetailsScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}