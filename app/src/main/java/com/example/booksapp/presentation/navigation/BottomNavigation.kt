package com.example.booksapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.booksapp.R

sealed class BottomNavItem(
    val route: String,
    val titleRes: Int
) {
    object Books : BottomNavItem(
        route = Screens.Books.route,
        titleRes = R.string.books
    )

    object Favorites : BottomNavItem(
        route = Screens.Favorites.route,
        titleRes = R.string.favorites
    )

    object Filters : BottomNavItem(
        route = Screens.Filters.route,
        titleRes = R.string.filters
    )

    object Profile : BottomNavItem(
        route = Screens.Profile.route,
        titleRes = R.string.profile
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Books,
        BottomNavItem.Favorites,
        BottomNavItem.Filters,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    when (item) {
                        is BottomNavItem.Books -> {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = stringResource(id = item.titleRes)
                            )
                        }
                        is BottomNavItem.Favorites -> {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = stringResource(id = item.titleRes)
                            )
                        }
                        is BottomNavItem.Filters -> {
                            Icon(
                                imageVector = Icons.Default.FilterAlt,
                                contentDescription = stringResource(id = item.titleRes)
                            )
                        }
                        is BottomNavItem.Profile -> {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = stringResource(id = item.titleRes)
                            )
                        }
                    }
                },
                label = { Text(stringResource(id = item.titleRes)) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}