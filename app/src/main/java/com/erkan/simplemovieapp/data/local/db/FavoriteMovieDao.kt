package com.erkan.simplemovieapp.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erkan.simplemovieapp.data.local.models.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoriteMovieEntity)

    @Delete
    suspend fun removeFavoriteMovie(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies ORDER BY releaseDate DESC")
    fun getAllFavoriteMovies(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :movieId)")
    fun isMovieFavorite(movieId: Int): Flow<Boolean>
}