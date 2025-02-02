package com.erkan.simplemovieapp.presentation.models

import android.os.Parcelable
import com.erkan.simplemovieapp.domain.models.Movies
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesUI(
    val page: Int = 0,
    val results: List<Result> = listOf(),
    val totalPages: Int = 0,
    val totalResults: Int = 0,
) : Parcelable {
    @Parcelize
    data class Result(
        val adult: Boolean = false,
        val backdropPath: String? = null,
        val genreIds: List<Int> = listOf(),
        val id: Int = 0,
        val originalLanguage: String = "",
        val originalTitle: String = "",
        val overview: String = "",
        val popularity: Double = 0.0,
        val posterPath: String = "",
        val releaseDate: String = "",
        val title: String = "",
        val video: Boolean = false,
        val voteAverage: Double = 0.0,
        val voteCount: Int = 0,
    ) : Parcelable
}

fun Movies.toUI(): MoviesUI = MoviesUI(
    page = page,
    results = results.map { it.toUI() },
    totalPages = totalPages,
    totalResults = totalResults,
)

fun Movies.Result.toUI(): MoviesUI.Result = MoviesUI.Result(
    adult,
    backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)