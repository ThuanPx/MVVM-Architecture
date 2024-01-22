package com.thuanpx.view_mvvm_architecture.base.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber

object AppErrors {

    private val adapter = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build().adapter(
        ErrorJson::class.java)

    @JvmStatic
    fun from(throwable: Throwable?): NetworkError? {
        if (throwable is HttpException) {
            val errorBody = throwable.response()?.errorBody() ?: return null
            try {
                val error = adapter.fromJson(errorBody.string())
                if (error != null) {
                    return NetworkError(error.error_code, error.message, error.status)
                }
            } catch (e: Exception) {
               Timber.e( e.message ?: "")
            }
        }
        return null
    }

    @JvmStatic
    fun fromThrowable(throwable: Throwable?): NetworkError? {
        var errorBody: ResponseBody? = null
        var httpCode = String()

        when (throwable) {
            is HttpException -> {
                errorBody = throwable.response()?.errorBody() ?: return null
                httpCode = throwable.code().toString()
            }
            is RetrofitException -> {
                errorBody = throwable.response?.errorBody() ?: return null
                httpCode = throwable.response.code().toString()
            }
        }
        try {
            errorBody?.let { body ->
                val error = adapter.fromJson(body.string())
                if (error != null) {
                    val code: String = error.error_code ?: httpCode
                    val message = error.message ?: error.error
                    return NetworkError(code, message, error.status)
                }
            }
        } catch (e: Exception) {
            Timber.e( e.message ?: "")
        }

        return null
    }
}