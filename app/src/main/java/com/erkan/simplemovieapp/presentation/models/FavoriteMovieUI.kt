package com.erkan.simplemovieapp.presentation.models

import com.erkan.simplemovieapp.domain.models.FavoriteMovie

data class FavoriteMovieUI(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val originalTitle: String,
    val language: String,
    val voteCount: Int
)

fun FavoriteMovieUI.toMovieResult() = MoviesUI.Result(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    originalTitle = originalTitle,
    originalLanguage = language,
    voteCount = voteCount
)

fun FavoriteMovie.toUI(): FavoriteMovieUI = FavoriteMovieUI(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    originalTitle = originalTitle,
    language = language,
    voteCount = voteCount
)

fun FavoriteMovieUI.toDomain(): FavoriteMovie = FavoriteMovie(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    originalTitle = originalTitle,
    language = language,
    voteCount = voteCount
)