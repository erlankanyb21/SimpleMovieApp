package com.erkan.simplemovieapp.domain.usecase

import com.erkan.simplemovieapp.domain.models.FavoriteMovie
import com.erkan.simplemovieapp.domain.repository.FavoriteMovieRepository

class DeleteFavoriteMovieUseCase(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    operator fun invoke(movie: FavoriteMovie) =
        favoriteMovieRepository.removeFavoriteMovie(movie)
}