package com.erkan.simplemovieapp.domain.usecase

import com.erkan.simplemovieapp.domain.repository.MovieRepository

class FetchPagingMovieUseCase(
    private val movieRepository: MovieRepository
) {
    operator fun invoke() =
        movieRepository.fetchMovies()
}