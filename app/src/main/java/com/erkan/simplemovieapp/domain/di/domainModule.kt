package com.erkan.simplemovieapp.domain.di

import org.koin.dsl.module
import com.erkan.simplemovieapp.domain.usecase.FetchPagingMovieUseCase
import org.koin.core.module.dsl.factoryOf

val domainModule = module {
    factoryOf(::FetchPagingMovieUseCase)
}