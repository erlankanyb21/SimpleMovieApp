package com.erkan.simplemovieapp.data.di

import com.erkan.simplemovieapp.BuildConfig
import com.erkan.simplemovieapp.data.network.MovieApiService
import com.erkan.simplemovieapp.domain.repository.MovieRepository
import com.erkan.simplemovieapp.data.repository.MovieRepositoryImpl
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

    singleOf(::MovieRepositoryImpl) { bind<MovieRepository>() }
}

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