package com.erkan.simplemovieapp.domain.usecase

import com.erkan.simplemovieapp.domain.models.FavoriteMovie
import com.erkan.simplemovieapp.domain.repository.FavoriteMovieRepository

class InsertFavoriteMovieUseCase(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    operator fun invoke(movie: FavoriteMovie) =
        favoriteMovieRepository.insertFavoriteMovie(movie)
}