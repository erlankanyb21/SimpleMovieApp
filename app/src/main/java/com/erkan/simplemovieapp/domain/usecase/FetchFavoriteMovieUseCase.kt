package com.erkan.simplemovieapp.domain.usecase

import com.erkan.simplemovieapp.domain.repository.FavoriteMovieRepository

class FetchFavoriteMovieUseCase(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    operator fun invoke() =
        favoriteMovieRepository.fetchFavoriteMovies()
}