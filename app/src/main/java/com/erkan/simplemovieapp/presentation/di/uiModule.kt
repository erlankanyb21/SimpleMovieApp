package com.erkan.simplemovieapp.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.erkan.simplemovieapp.presentation.fragments.MovieViewModel

val uiModule = module {
    viewModelOf(::MovieViewModel)
}