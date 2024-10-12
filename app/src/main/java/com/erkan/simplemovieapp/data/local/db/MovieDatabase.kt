package com.erkan.simplemovieapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.erkan.simplemovieapp.data.local.GenreIdsConverter
import com.erkan.simplemovieapp.data.local.models.MovieEntity
import com.erkan.simplemovieapp.data.local.models.MovieResultEntity

@Database(entities = [MovieEntity::class, MovieResultEntity::class], version = 1, exportSchema = false)
@TypeConverters(GenreIdsConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}