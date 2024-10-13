package com.erkan.simplemovieapp.domain.models


data class FavoriteMovie(
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