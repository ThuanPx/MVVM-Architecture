package com.thuanpx.mvvm_architecture.common.base

import com.thuanpx.mvvm_architecture.utils.DataResult
import com.thuanpx.mvvm_architecture.utils.liveData.SingleEvent
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {

    /**
     * Make template code to get DataResult return to ViewModel
     * Support for call api, get data from database
     * Handle exceptions: Convert exception to Result.Error
     * Avoid duplicate code
     *
     * Default CoroutineContext is IO for repository
     */
    protected suspend fun <R> withResultContext(
        context: CoroutineContext = Dispatchers.IO,
        requestBlock: suspend CoroutineScope.() -> R,
        errorBlock: (suspend CoroutineScope.(Exception) -> DataResult.Error)? = null
    ): DataResult<SingleEvent<R>> = withContext(context) {
        return@withContext try {
            val response = requestBlock()
            DataResult.Success(SingleEvent(response))
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext errorBlock?.invoke(this, e) ?: DataResult.Error(e)
        }
    }

    protected suspend fun <R> withResultContext(
        context: CoroutineContext = Dispatchers.IO,
        requestBlock: suspend CoroutineScope.() -> R
    ): DataResult<SingleEvent<R>> = withResultContext(context, requestBlock, null)
}
