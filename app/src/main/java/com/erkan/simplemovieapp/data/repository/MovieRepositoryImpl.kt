package com.erkan.simplemovieapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.erkan.simplemovieapp.data.base.BaseRepository
import com.erkan.simplemovieapp.data.local.db.MovieDao
import com.erkan.simplemovieapp.data.local.models.toDomain
import com.erkan.simplemovieapp.data.network.MovieApiService
import com.erkan.simplemovieapp.data.network.pagingSource.MovieRemoteMediator
import com.erkan.simplemovieapp.domain.models.Movies
import com.erkan.simplemovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImpl(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao,
) : MovieRepository, BaseRepository() {
    override fun fetchMovies(): Flow<PagingData<Movies.Result>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            remoteMediator = MovieRemoteMediator(movieApiService, movieDao),
            pagingSourceFactory = { movieDao.getPagedMovies() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }

    override fun insertMovie(movie: Movies.Result) {
    }
}