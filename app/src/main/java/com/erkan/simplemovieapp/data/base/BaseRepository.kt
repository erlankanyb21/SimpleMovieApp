package com.erkan.simplemovieapp.data.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.erkan.simplemovieapp.common.Either
import kotlinx.coroutines.flow.flow

abstract class BaseRepository {

    protected fun <T> doRequest(action: suspend () -> T) = flow {
        try {
            emit(Either.Right(action()))
        } catch (e: Exception) {
            emit(Either.Left(e.localizedMessage as String))
        }
    }

    /**
     * [makePagingRequest] создает объект Pager с конфигурацией PagingConfig и возвращает объект
     * Flow<PagingData<Value>>, который представляет пагинированные данные. Она принимает параметры:
     * @param pagingSource представляет источник данных для пагинации.
     * @param pageSize: размер страницы.
     * @param prefetchDistance: расстояние, на котором должен начаться предварительный запрос следующей страницы.
     * @param enablePlaceholders: показывает, должны ли быть включены заглушки.
     * @param initialLoadSize: начальная загрузка данных в пагинационном источнике.
     * @param maxSize: максимальный размер списка.
     * @param jumpThreshold: пороговое значение для перехода на конкретную страницу в списке.
     */
    protected fun <Value : Any> makePagingRequest(
        pagingSource: PagingSource<Int, Value>,
        pageSize: Int = 15,
        prefetchDistance: Int = pageSize,
        enablePlaceholders: Boolean = true,
        initialLoadSize: Int = pageSize * 3,
        maxSize: Int = Int.MAX_VALUE,
        jumpThreshold: Int = Int.MIN_VALUE,
    ) = Pager(config = PagingConfig(
        pageSize, prefetchDistance, enablePlaceholders, initialLoadSize, maxSize, jumpThreshold
    ), pagingSourceFactory = {
        pagingSource
    }).flow
}