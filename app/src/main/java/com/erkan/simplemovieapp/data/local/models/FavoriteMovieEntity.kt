package com.erkan.simplemovieapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erkan.simplemovieapp.common.DataMapper
import com.erkan.simplemovieapp.domain.models.FavoriteMovie

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String,
    val originalTitle: String,
    val language: String,
    val voteAverage: Double,
    val voteCount: Int
) : DataMapper<FavoriteMovie> {
    override fun toDomain(): FavoriteMovie =
        FavoriteMovie(
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
}

fun FavoriteMovie.toEntity(): FavoriteMovieEntity =
    FavoriteMovieEntity(
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