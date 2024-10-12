package com.erkan.simplemovieapp.data.network.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.erkan.simplemovieapp.data.network.MovieApiService
import com.erkan.simplemovieapp.domain.models.Movies

class MoviePagingSource(
    private val movieApiService: MovieApiService
) : PagingSource<Int, Movies.Result>() {

    override fun getRefreshKey(state: PagingState<Int, Movies.Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies.Result> {
        return try {
            val page = params.key ?: 1
            val response = movieApiService.fetchMovies(page = page).toDomain()

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (page < response.totalPages) page.plus(1) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}