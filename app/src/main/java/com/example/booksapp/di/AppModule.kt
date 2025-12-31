package com.example.booksapp.di

import android.content.Context
import com.example.booksapp.data.local.database.AppDatabase
import com.example.booksapp.data.remote.api.BooksApi
import com.example.booksapp.data.repository.BooksRepositoryImpl
import com.example.booksapp.data.repository.ProfileRepositoryImpl
import com.example.booksapp.domain.repository.BooksRepository
import com.example.booksapp.domain.repository.ProfileRepository
import com.example.booksapp.domain.usecase.GetBooksUseCase
import com.example.booksapp.domain.usecase.GetFavoritesUseCase
import com.example.booksapp.domain.usecase.GetGenresUseCase
import com.example.booksapp.domain.usecase.GetProfileUseCase
import com.example.booksapp.domain.usecase.SaveProfileUseCase
import com.example.booksapp.domain.usecase.ToggleFavoriteUseCase
import com.example.booksapp.presentation.books.BooksViewModel
import com.example.booksapp.presentation.common.FilterCache
import com.example.booksapp.presentation.details.BookDetailsViewModel
import com.example.booksapp.presentation.favorites.FavoritesViewModel
import com.example.booksapp.presentation.filters.FiltersViewModel
import com.example.booksapp.presentation.profile.ProfileViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule: Module = module {
    single(named("applicationScope")) {
        CoroutineScope(SupervisorJob())
    }

    single { FilterCache() }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // BooksApi
    single<BooksApi> {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create(BooksApi::class.java)
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl(
            context = androidContext()
        )
    }

    factory { GetProfileUseCase(get()) }
    factory { SaveProfileUseCase(get()) }


    single {
        AppDatabase.getDatabase(androidContext())
    }

    single { get<AppDatabase>().favoriteDao() }

    single<BooksRepository> {
        BooksRepositoryImpl(
            api = get(),
            favoriteDao = get()
        )
    }

    factory { GetBooksUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }
    factory { GetGenresUseCase(get()) }
    factory { GetFavoritesUseCase(get()) }

    viewModel {
        BooksViewModel(
            getBooksUseCase = get(),
            toggleFavoriteUseCase = get(),
            filterCache = get()
        )
    }

    viewModel {
        FavoritesViewModel(
            getFavoritesUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }

    viewModel {
        FiltersViewModel(
            getGenresUseCase = get(),
            filterCache = get()
        )
    }

    viewModel {
        BookDetailsViewModel(
            repository = get(),
            toggleFavoriteUseCase = get()
        )
    }

    viewModel {
        ProfileViewModel(
            getProfileUseCase = get(),
            saveProfileUseCase = get()
        )
    }
}