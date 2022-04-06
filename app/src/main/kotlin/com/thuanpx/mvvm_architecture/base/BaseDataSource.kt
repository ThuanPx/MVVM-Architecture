package com.thuanpx.mvvm_architecture.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thuanpx.ktext.flow.loading
import com.thuanpx.mvvm_architecture.model.response.BaseResponse
import com.thuanpx.mvvm_architecture.utils.coroutines.ApiResponse
import com.thuanpx.mvvm_architecture.utils.coroutines.suspendOnSuccessAutoError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException


/**
 * Created by ThuanPx on 4/3/22.
 */

abstract class BaseDataSource<T : Any> : PagingSource<Int, T>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

    abstract suspend fun requestMore(nextPage: Int): ApiResponse<BaseResponse<List<T>>>
    abstract val loading: MutableLiveData<Boolean>

    private var apiResponse: MutableSharedFlow<ApiResponse<BaseResponse<List<T>>>>? = null


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX

        return try {
            var prevKey: Int? = null
            var nextKey: Int? = null
            var items = listOf<T>()
            flow<ApiResponse<BaseResponse<List<T>>>> {
                requestMore(nextPage = pageNumber)
                    .suspendOnSuccessAutoError {
                        items = this.data.data
                        // Since 0 is the lowest page number, return null to signify no more pages should
                        prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber.minus(1)

                        // data, we return `null` to signify no more pages should be loaded
                        nextKey = if (items.isNotEmpty()) pageNumber + 1 else null

                    }
            }
                .loading(loading)
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
