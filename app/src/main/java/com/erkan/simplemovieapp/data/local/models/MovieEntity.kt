package com.erkan.simplemovieapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.erkan.simplemovieapp.data.local.GenreIdsConverter
import com.erkan.simplemovieapp.domain.models.Movies

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val page: Int,
    val totalPages: Int,
    val totalResults: Int
)

@Entity(tableName = "movie_results")
data class MovieResultEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    @TypeConverters(GenreIdsConverter::class)
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val page: Int // Для связи с MovieEntity
)

fun MovieResultEntity.toDomain(): Movies.Result {
    return Movies.Result(
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

