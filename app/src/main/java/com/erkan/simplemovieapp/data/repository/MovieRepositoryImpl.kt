package com.erkan.simplemovieapp.data.repository

import androidx.paging.PagingData
import com.erkan.simplemovieapp.data.base.BaseRepository
import com.erkan.simplemovieapp.data.network.MovieApiService
import com.erkan.simplemovieapp.data.network.pagingSource.MoviePagingSource
import com.erkan.simplemovieapp.domain.models.Movies
import com.erkan.simplemovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val movieApiService: MovieApiService
) : MovieRepository, BaseRepository() {
    override fun fetchMovies(): Flow<PagingData<Movies.Result>> = makePagingRequest(
        pagingSource = MoviePagingSource(movieApiService)
    )
}