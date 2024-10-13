package com.erkan.simplemovieapp.domain.repository

import com.erkan.simplemovieapp.common.Either
import com.erkan.simplemovieapp.domain.models.FavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoriteMovieRepository {
    fun fetchFavoriteMovies(): Flow<Either<String,List<FavoriteMovie>>>
    fun insertFavoriteMovie(movie: FavoriteMovie): Flow<Either<String, Unit>>
    fun removeFavoriteMovie(movie: FavoriteMovie): Flow<Either<String, Unit>>
    fun isMovieFavorite(movieId: Int): Flow<Either<String, Boolean>>
}