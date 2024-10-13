package com.erkan.simplemovieapp.data.di

import android.content.Context
import androidx.room.Room
import com.erkan.simplemovieapp.BuildConfig
import com.erkan.simplemovieapp.data.local.db.MovieDatabase
import com.erkan.simplemovieapp.data.network.MovieApiService
import com.erkan.simplemovieapp.domain.repository.MovieRepository
import com.erkan.simplemovieapp.data.repository.MovieRepositoryImpl
import com.erkan.simplemovieapp.data.repository.FavoriteMovieRepositoryImpl
import com.erkan.simplemovieapp.domain.repository.FavoriteMovieRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    factoryOf(::provideOkhttp)
    singleOf(::provideMovieApiService)
    singleOf(::MovieRepositoryImpl) {
        bind<MovieRepository>()
    }
    singleOf(::FavoriteMovieRepositoryImpl) {
        bind<FavoriteMovieRepository>()
    }
    singleOf(::provideMovieDatabase)
    factoryOf(::provideMovieDao)
    factoryOf(::provideFavoriteMovieDao)
}

fun provideMovieDatabase(context: Context): MovieDatabase {
    return Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
}

fun provideMovieDao(movieDatabase: MovieDatabase) = movieDatabase.movieDao()
fun provideFavoriteMovieDao(movieDatabase: MovieDatabase) = movieDatabase.favoriteMovieDao()

fun provideOkhttp(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    return OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}

fun provideMovieApiService(okHttpClient: OkHttpClient): MovieApiService {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(MovieApiService::class.java)
}