package com.erkan.simplemovieapp.domain.di

import org.koin.dsl.module
import com.erkan.simplemovieapp.domain.usecase.FetchPagingMovieUseCase
import com.erkan.simplemovieapp.domain.usecase.InsertFavoriteMovieUseCase
import com.erkan.simplemovieapp.domain.usecase.DeleteFavoriteMovieUseCase
import com.erkan.simplemovieapp.domain.usecase.FetchFavoriteMovieUseCase
import com.erkan.simplemovieapp.domain.usecase.CheckIsFavoriteMovieUseCase
import org.koin.core.module.dsl.factoryOf

val domainModule = module {
    factoryOf(::FetchPagingMovieUseCase)
    factoryOf(::InsertFavoriteMovieUseCase)
    factoryOf(::DeleteFavoriteMovieUseCase)
    factoryOf(::FetchFavoriteMovieUseCase)
    factoryOf(::CheckIsFavoriteMovieUseCase)
}