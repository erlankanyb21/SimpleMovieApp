package com.erkan.simplemovieapp.data.local.db


import androidx.paging.PagingSource
import androidx.room.*
import com.erkan.simplemovieapp.data.local.models.MovieEntity
import com.erkan.simplemovieapp.data.local.models.MovieResultEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieResults(results: List<MovieResultEntity>)

    @Query("SELECT * FROM movies WHERE page = :page")
    suspend fun getMovieByPage(page: Int): MovieEntity?

    @Query("SELECT * FROM movie_results ORDER BY page ASC")
    fun getPagedMovies(): PagingSource<Int, MovieResultEntity>

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

    @Query("DELETE FROM movie_results")
    suspend fun clearMovieResults()
}