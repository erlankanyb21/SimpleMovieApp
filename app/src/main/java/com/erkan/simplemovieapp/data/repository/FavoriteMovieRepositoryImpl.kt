package com.erkan.simplemovieapp.data.repository

import com.erkan.simplemovieapp.common.Either
import com.erkan.simplemovieapp.data.base.BaseRepository
import com.erkan.simplemovieapp.data.local.db.FavoriteMovieDao
import com.erkan.simplemovieapp.data.local.models.toEntity
import com.erkan.simplemovieapp.domain.models.FavoriteMovie
import com.erkan.simplemovieapp.domain.repository.FavoriteMovieRepository
import kotlinx.coroutines.flow.Flow

class FavoriteMovieRepositoryImpl(
    private val favoriteMovieDao: FavoriteMovieDao,
) : FavoriteMovieRepository, BaseRepository() {
    override fun fetchFavoriteMovies(): Flow<Either<String, List<FavoriteMovie>>> = makeSafeCall(
        daoCall = { favoriteMovieDao.getAllFavoriteMovies() },
        domainMapper = { it.toDomain() }
    )

    override fun insertFavoriteMovie(movie: FavoriteMovie) = doRequest {
        favoriteMovieDao.insertFavoriteMovie(movie.toEntity())
    }

    override fun removeFavoriteMovie(movie: FavoriteMovie) = doRequest {
        favoriteMovieDao.removeFavoriteMovie(movie.toEntity())
    }

    override fun isMovieFavorite(movieId: Int): Flow<Either<String, Boolean>> = makeRequest(
        moveId = movieId,
        daoCall = { favoriteMovieDao.isMovieFavorite(movieId) }
    )
}