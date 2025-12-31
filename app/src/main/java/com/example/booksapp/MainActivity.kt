package com.example.booksapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.booksapp.di.appModule
import com.example.booksapp.presentation.navigation.BottomNavigationBar
import com.example.booksapp.presentation.navigation.NavGraph
import com.example.booksapp.ui.theme.BooksAppTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate started")

        initializeKoinIfNeeded()

        setContent {
            BooksAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BooksAppContent()
                }
            }
        }

        Log.d("MainActivity", "onCreate completed")
    }

    private fun initializeKoinIfNeeded() {
        try {
            val koin = org.koin.core.context.GlobalContext.getOrNull()

            if (koin == null) {
                Log.d("MainActivity", "Initializing Koin...")
                startKoin {
                    androidLogger(Level.ERROR)
                    androidContext(this@MainActivity)
                    modules(appModule)
                }
                Log.d("MainActivity", "Koin initialized successfully")
            } else {
                Log.d("MainActivity", "Koin already initialized, skipping initialization")
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error during Koin initialization", e)
        }
    }
}

@androidx.compose.runtime.Composable
fun BooksAppContent() {
    val navController = rememberNavController()

    androidx.compose.material3.Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}