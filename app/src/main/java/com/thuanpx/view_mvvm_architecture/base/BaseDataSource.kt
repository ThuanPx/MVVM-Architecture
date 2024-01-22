package com.thuanpx.view_mvvm_architecture.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thuanpx.view_mvvm_architecture.model.response.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by ThuanPx on 4/3/22.
 */

abstract class BaseDataSource<T : Any> : PagingSource<Int, T>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 0
        private const val LOAD_DELAY_MILLIS = 2_000L
    }

    abstract suspend fun requestMore(nextPage: Int): BaseResponse<List<T>>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {

        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        if (pageNumber != STARTING_PAGE_INDEX) delay(LOAD_DELAY_MILLIS)

        return try {
            var prevKey: Int? = null
            var nextKey: Int? = null
            var items = listOf<T>()
            flow<BaseResponse<List<T>>> {
                items = requestMore(nextPage = pageNumber).data ?: emptyList()
                // Since 0 is the lowest page number, return null to signify no more pages should
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber.minus(1)
                // data, we return `null` to signify no more pages should be loaded
                nextKey = if (items.isNotEmpty()) pageNumber + 1 else null
            }
                .flowOn(Dispatchers.IO)
                .collect()

            LoadResult.Page(
                data = items,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        } catch (e: Throwable) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
