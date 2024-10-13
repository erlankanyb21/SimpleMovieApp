package com.erkan.simplemovieapp.presentation.fragments

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.erkan.simplemovieapp.domain.usecase.CheckIsFavoriteMovieUseCase
import com.erkan.simplemovieapp.domain.usecase.DeleteFavoriteMovieUseCase
import com.erkan.simplemovieapp.domain.usecase.FetchFavoriteMovieUseCase
import com.erkan.simplemovieapp.domain.usecase.FetchPagingMovieUseCase
import com.erkan.simplemovieapp.domain.usecase.InsertFavoriteMovieUseCase
import com.erkan.simplemovieapp.presentation.base.BaseViewModel
import com.erkan.simplemovieapp.presentation.models.FavoriteMovieUI
import com.erkan.simplemovieapp.presentation.models.toDomain
import com.erkan.simplemovieapp.presentation.models.toUI
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class MovieViewModel(
    private val fetchPagingMovieUseCase: FetchPagingMovieUseCase,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val fetchFavoriteMovieUseCase: FetchFavoriteMovieUseCase,
    private val checkIsFavoriteMovieUseCase: CheckIsFavoriteMovieUseCase
) : BaseViewModel() {

    private val _insertState = mutableUiStateFlow<Unit>()
    val insertState = _insertState.asStateFlow()

    private val _deleteState = mutableUiStateFlow<Unit>()
    val deleteState = _deleteState.asStateFlow()

    private val _isFavoriteState = mutableUiStateFlow<Boolean>()
    val isFavoriteState = _isFavoriteState.asStateFlow()

    private val _favoriteMoviesState = mutableUiStateFlow<List<FavoriteMovieUI>>()
    val favoriteMoviesState = _favoriteMoviesState.asStateFlow()

    fun pagingMovies() = fetchPagingMovieUseCase()
        .map { pagingData ->
            pagingData.map { it.toUI() }
        }
        .cachedIn(viewModelScope)

    fun insertFavoriteMovie(movie: FavoriteMovieUI) =
        insertFavoriteMovieUseCase(movie.toDomain()).gatherRequest(_insertState) {}

    fun deleteFavoriteMovie(movie: FavoriteMovieUI) =
        deleteFavoriteMovieUseCase(movie.toDomain()).gatherRequest(_deleteState) {}

    fun checkIsFavoriteMovie(movieId: Int) =
        checkIsFavoriteMovieUseCase(movieId).gatherRequest(_isFavoriteState) {
            it
        }

    fun fetchFavoriteMovies() =
        fetchFavoriteMovieUseCase().gatherRequest(_favoriteMoviesState) { list ->
            list.map { it.toUI() }
        }
}