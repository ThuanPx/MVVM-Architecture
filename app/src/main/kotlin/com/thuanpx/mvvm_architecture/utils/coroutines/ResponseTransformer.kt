package com.thuanpx.mvvm_architecture.utils.coroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thuanpx.mvvm_architecture.utils.coroutines.coroutinesAdapter.SuspensionFunction
import com.thuanpx.mvvm_architecture.utils.coroutines.exceptions.ErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Returns a response callback from an onResult lambda.
 *
 * @param onResult A lambda that would be executed when the request finished.
 *
 * @return A [Callback] will be executed.
 */
@PublishedApi
@JvmSynthetic
internal inline fun <T> getCallbackFromOnResultOnCoroutinesScope(
    coroutineScope: CoroutineScope,
    crossinline onResult: suspend (response: ApiResponse<T>) -> Unit
): Callback<T> {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            coroutineScope.launch {
                onResult(ApiResponse.of { response })
            }
        }

        override fun onFailure(call: Call<T>, throwable: Throwable) {
            coroutineScope.launch {
                onResult(ApiResponse.error(throwable))
            }
        }
    }
}

/**
 * Returns a response callback from an onResult lambda.
 *
 * @param onResult A lambda that would be executed when the request finished.
 *
 * @return A [Callback] will be executed.
 */
@PublishedApi
@JvmSynthetic
internal inline fun <T> getCallbackFromOnResultWithContext(
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline onResult: suspend (response: ApiResponse<T>) -> Unit
): Callback<T> {
    return object : Callback<T> {
        val supervisorJob = SupervisorJob(context[Job])
        val scope = CoroutineScope(context + supervisorJob)
        override fun onResponse(call: Call<T>, response: Response<T>) {
            scope.launch {
                onResult(ApiResponse.of { response })
            }
        }

        override fun onFailure(call: Call<T>, throwable: Throwable) {
            scope.launch {
                onResult(ApiResponse.error(throwable))
            }
        }
    }
}

/**
 * Returns the encapsulated data if this instance represents [ApiResponse.Success] or
 * returns null if it is [ApiResponse.Error] or [ApiResponse.Error].
 *
 * @return The encapsulated data or null.
 */
public fun <T> ApiResponse<T>.dataOrNull(): T? {
    return when (this) {
        is ApiResponse.Success -> data
        is ApiResponse.Error -> null
    }
}

/**
 * Returns the encapsulated data if this instance represents [ApiResponse.Success] or
 * returns the [defaultValue] if it is [ApiResponse.Error].
 *
 * @return The encapsulated data or [defaultValue].
 */
public fun <T> ApiResponse<T>.dataOrElse(defaultValue: T): T {
    return when (this) {
        is ApiResponse.Success -> data
        is ApiResponse.Error -> defaultValue
    }
}

/**
 * Returns the encapsulated data if this instance represents [ApiResponse.Success] or
 * invokes the lambda [defaultValue] that returns [T] if it is [ApiResponse.Error].
 *
 * @return The encapsulated data or [defaultValue].
 */
public inline fun <T> ApiResponse<T>.dataOrElse(defaultValue: () -> T): T {
    return when (this) {
        is ApiResponse.Success -> data
        is ApiResponse.Error -> defaultValue()
    }
}

/**
 * Returns the encapsulated data if this instance represents [ApiResponse.Success] or
 * throws the encapsulated Throwable exception if it is [ApiResponse.Error].
 *
 * @throws [ApiResponse.Error]
 *
 * @return The encapsulated data.
 */
public fun <T> ApiResponse<T>.dataOrException(): T {
    when (this) {
        is ApiResponse.Success -> return data
        is ApiResponse.Error -> throw ErrorResponse(this.statusCode.code, this.message)
    }
}

/**
 * A scope function that would be executed for handling successful responses if the request succeeds.
 *
 * @param onResult The receiver function that receiving [ApiResponse.Success] if the request succeeds.
 *
 * @return The original [ApiResponse].
 */
@JvmSynthetic
public inline fun <T> ApiResponse<T>.onSuccess(
    crossinline onResult: ApiResponse.Success<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        onResult(this)
    }
    return this
}

/**
 * A suspension scope function that would be executed for handling successful responses if the request succeeds.
 *
 * @param onResult The receiver function that receiving [ApiResponse.Success] if the request succeeds.
 *
 * @return The original [ApiResponse].
 */
@JvmSynthetic
@SuspensionFunction
public suspend inline fun <T> ApiResponse<T>.suspendOnSuccess(
    crossinline onResult: suspend ApiResponse.Success<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        onResult(this)
    }
    return this
}

@JvmSynthetic
@SuspensionFunction
public suspend inline fun <T> ApiResponse<T>.suspendOnSuccessAutoError(
    crossinline onResult: suspend ApiResponse.Success<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        onResult(this)
    } else if (this is ApiResponse.Error) {
        throw ErrorResponse(this.statusCode.code, this.message)
    }
    return this
}

/**
 * A suspension function that would be executed for handling error responses if the request failed or get an exception.
 *
 * @param onResult The receiver function that receiving [ApiResponse.Error] if the request failed or get an exception.
 *
 * @return The original [ApiResponse].
 */
@JvmSynthetic
@SuspensionFunction
public suspend inline fun <T> ApiResponse<T>.suspendOnError(
    crossinline onResult: suspend String.() -> Unit
): ApiResponse<T> {
    suspendOnException { onResult(message()) }
    return this
}

/**
 * A scope function that would be executed for handling exception responses if the request get an exception.
 *
 * @param onResult The receiver function that receiving [ApiResponse.Error] if the request get an exception.
 *
 * @return The original [ApiResponse].
 */
@JvmSynthetic
public inline fun <T> ApiResponse<T>.onError(
    crossinline onResult: ApiResponse.Error<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Error) {
        onResult(this)
    }
    return this
}

/**
 * A suspension scope function that would be executed for handling exception responses if the request get an exception.
 *
 * @param onResult The receiver function that receiving [ApiResponse.Error] if the request get an exception.
 *
 * @return The original [ApiResponse].
 */
@JvmSynthetic
@SuspensionFunction
public suspend inline fun <T> ApiResponse<T>.suspendOnException(
    crossinline onResult: suspend ApiResponse.Error<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Error) {
        onResult(this)
    }
    return this
}

/**
 * A scope function that will be executed for handling successful, error, exception responses.
 *  This function receives and handles [ApiResponse.onSuccess], [ApiResponse.onError].
 *
 * @param onSuccess A scope function that would be executed for handling successful responses if the request succeeds.
 * @param onException A scope function that would be executed for handling exception responses if the request get an exception.
 *
 *  @return The original [ApiResponse].
 */
@JvmSynthetic
public inline fun <T> ApiResponse<T>.onProcedure(
    crossinline onSuccess: ApiResponse.Success<T>.() -> Unit,
    crossinline onException: ApiResponse.Error<T>.() -> Unit
): ApiResponse<T> = apply {
    this.onSuccess(onSuccess)
    this.onError(onException)
}

/**
 * A suspension scope function that will be executed for handling successful, error, exception responses.
 *  This function receives and handles [ApiResponse.onSuccess], [ApiResponse.onError],
 *  and [ApiResponse.onException] in one scope.
 *
 * @param onSuccess A suspension scope function that would be executed for handling successful responses if the request succeeds.
 * @param onError A suspension scope function that would be executed for handling error responses if the request failed.
 * @param onException A suspension scope function that would be executed for handling exception responses if the request get an exception.
 *
 *  @return The original [ApiResponse].
 */
@JvmSynthetic
@SuspensionFunction
public suspend inline fun <T> ApiResponse<T>.suspendOnProcedure(
    crossinline onSuccess: suspend ApiResponse.Success<T>.() -> Unit,
    crossinline onException: suspend ApiResponse.Error<T>.() -> Unit
): ApiResponse<T> = apply {
    this.suspendOnSuccess(onSuccess)
    this.suspendOnException(onException)
}

/**
 * Merges multiple [ApiResponse]s as one [ApiResponse] depending on the policy, [ApiResponseMergePolicy].
 * The default policy is [ApiResponseMergePolicy.IGNORE_FAILURE].
 *
 * @param responses Responses for merging as one [ApiResponse].
 * @param mergePolicy A policy for merging response data depend on the success or not.
 *
 * @return [ApiResponse] that depends on the [ApiResponseMergePolicy].
 */
@JvmSynthetic
public fun <T> ApiResponse<List<T>>.merge(
    vararg responses: ApiResponse<List<T>>,
    mergePolicy: ApiResponseMergePolicy = ApiResponseMergePolicy.IGNORE_FAILURE
): ApiResponse<List<T>> {
    val apiResponses = responses.toMutableList()
    apiResponses.add(0, this)

    var apiResponse: ApiResponse.Success<List<T>> =
        ApiResponse.Success(Response.success(mutableListOf(), Headers.headersOf()))

    val data: MutableList<T> = mutableListOf()

    for (response in apiResponses) {
        if (response is ApiResponse.Success) {
            data.addAll(response.data)
            apiResponse = ApiResponse.Success(
                Response.success(data, response.headers)
            )
        } else if (mergePolicy === ApiResponseMergePolicy.PREFERRED_FAILURE) {
            return response
        }
    }

    return apiResponse
}

/**
 * Returns an error message from the [ApiResponse.Error] that consists of the localized message.
 *
 * @return An error message from the [ApiResponse.Error].
 */
public fun <T> ApiResponse.Error<T>.message(): String = toString()

/**
 * Returns a [LiveData] which contains successful data if the response is a [ApiResponse.Success].
 *
 * @return An observable [LiveData] which contains successful data.
 */
public fun <T> ApiResponse<T>.toLiveData(): LiveData<T> {
    val liveData = MutableLiveData<T>()
    if (this is ApiResponse.Success) {
        liveData.postValue(data)
    }
    return liveData
}

/**
 * Returns a [LiveData] which contains transformed data using successful data if the response is a [ApiResponse.Success].
 *
 * @param transformer A transformer lambda receives successful data and returns anything.
 *
 * @return An observable [LiveData] which contains successful data.
 */
@JvmSynthetic
public inline fun <T, R> ApiResponse<T>.toLiveData(
    crossinline transformer: T.() -> R
): LiveData<R> {
    val liveData = MutableLiveData<R>()
    if (this is ApiResponse.Success) {
        liveData.postValue(data.transformer())
    }
    return liveData
}

/**
 * Returns a [LiveData] which contains transformed data using successful data if the response is a [ApiResponse.Success].
 *
 * @param transformer A suspension transformer lambda receives successful data and returns anything.
 *
 * @return An observable [LiveData] which contains successful data.
 */
@JvmSynthetic
@SuspensionFunction
public suspend inline fun <T, R> ApiResponse<T>.toSuspendLiveData(
    crossinline transformer: suspend T.() -> R
): LiveData<R> {
    val liveData = MutableLiveData<R>()
    if (this is ApiResponse.Success) {
        liveData.postValue(data.transformer())
    }
    return liveData
}

/**
 * Returns a [Flow] which emits successful data if the response is a [ApiResponse.Success] and the data is not null.
 *
 * @return A coroutines [Flow] which emits successful data.
 */
@JvmSynthetic
public fun <T> ApiResponse<T>.toFlow(): Flow<T> {
    return if (this is ApiResponse.Success) {
        flowOf(data)
    } else {
        emptyFlow()
    }
}

/**
 * Returns a [Flow] which contains transformed data using successful data
 * if the response is a [ApiResponse.Success] and the data is not null.
 *
 * @param transformer A transformer lambda receives successful data and returns anything.
 *
 * @return A coroutines [Flow] which emits successful data.
 */
@JvmSynthetic
public inline fun <T, R> ApiResponse<T>.toFlow(
    crossinline transformer: T.() -> R
): Flow<R> {
    return if (this is ApiResponse.Success) {
        flowOf(data.transformer())
    } else {
        emptyFlow()
    }
}

/**
 * Returns a [Flow] which contains transformed data using successful data
 * if the response is a [ApiResponse.Success] and the data is not null.
 *
 * @param transformer A suspension transformer lambda receives successful data and returns anything.
 *
 * @return A coroutines [Flow] which emits successful data.
 */
@JvmSynthetic
@SuspensionFunction
public suspend inline fun <T, R> ApiResponse<T>.toSuspendFlow(
    crossinline transformer: suspend T.() -> R
): Flow<R> {
    return if (this is ApiResponse.Success) {
        flowOf(data.transformer())
    } else {
        emptyFlow()
    }
}
