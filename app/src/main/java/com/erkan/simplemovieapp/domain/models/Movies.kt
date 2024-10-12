package com.erkan.simplemovieapp.domain.models

import com.erkan.simplemovieapp.common.UIMapper
import com.erkan.simplemovieapp.presentation.models.MoviesUI

data class Movies(
    val page: Int = 0,
    val results: List<Result> = listOf(),
    val totalPages: Int = 0,
    val totalResults: Int = 0
): UIMapper<MoviesUI> {
    override fun toUI(): MoviesUI = MoviesUI(
        page = page,
        results = results.map { it.toUI() },
        totalPages = totalPages,
        totalResults = totalResults
    )
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
        val voteCount: Int = 0
    ): UIMapper<MoviesUI.Result> {
        override fun toUI(): MoviesUI.Result = MoviesUI.Result(
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
    }
}