package com.erkan.simplemovieapp.presentation.fragments

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.erkan.simplemovieapp.domain.usecase.FetchPagingMovieUseCase
import com.erkan.simplemovieapp.presentation.base.BaseViewModel
import com.erkan.simplemovieapp.presentation.models.toUI
import kotlinx.coroutines.flow.map

class MovieViewModel(
    private val fetchPagingMovieUseCase: FetchPagingMovieUseCase,
) : BaseViewModel() {

    fun pagingMovies() = fetchPagingMovieUseCase()
        .map { pagingData ->
            pagingData.map { it.toUI() }
        }
        .cachedIn(viewModelScope)
}