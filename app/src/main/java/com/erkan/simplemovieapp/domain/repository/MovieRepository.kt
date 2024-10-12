package com.erkan.simplemovieapp.domain.repository

import androidx.paging.PagingData
import com.erkan.simplemovieapp.domain.models.Movies
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchMovies(): Flow<PagingData<Movies.Result>>
}