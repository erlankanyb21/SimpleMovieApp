package com.erkan.simplemovieapp.data.network.pagingSource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.erkan.simplemovieapp.data.local.db.MovieDao
import com.erkan.simplemovieapp.data.local.models.MovieEntity
import com.erkan.simplemovieapp.data.local.models.MovieResultEntity
import com.erkan.simplemovieapp.data.network.MovieApiService

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao
) : RemoteMediator<Int, MovieResultEntity>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieResultEntity>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.page?.plus(1) ?: 1
                }
            }

            val response = movieApiService.fetchMovies(page = page)
            val movies = response.toDomain()

            movieDao.insertMovie(MovieEntity(
                id = movies.page,
                page = movies.page,
                totalPages = movies.totalPages,
                totalResults = movies.totalResults
            ))
            movieDao.insertMovieResults(movies.results.map { 
                MovieResultEntity(
                    id = it.id,
                    adult = it.adult,
                    backdropPath = it.backdropPath,
                    genreIds = it.genreIds,
                    originalLanguage = it.originalLanguage,
                    originalTitle = it.originalTitle,
                    overview = it.overview,
                    popularity = it.popularity,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                    title = it.title,
                    video = it.video,
                    voteAverage = it.voteAverage,
                    voteCount = it.voteCount,
                    page = movies.page
                )
            })

            MediatorResult.Success(endOfPaginationReached = movies.page >= movies.totalPages)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}