package com.erkan.simplemovieapp.domain.usecase

import com.erkan.simplemovieapp.domain.repository.FavoriteMovieRepository

class CheckIsFavoriteMovieUseCase(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    operator fun invoke(movieId: Int) =
        favoriteMovieRepository.isMovieFavorite(movieId)
}