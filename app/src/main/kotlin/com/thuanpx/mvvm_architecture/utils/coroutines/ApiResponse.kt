package com.thuanpx.mvvm_architecture.utils.coroutines

import com.thuanpx.mvvm_architecture.utils.coroutines.exceptions.NoContentException
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * ApiResponse is an interface for constructing standard responses from the retrofit call.
 */
sealed class ApiResponse<out T> {

    /**
     * API Success response class from OkHttp request call.
     * The [data] is a nullable generic type. (A response without data)
     *
     * @param response A response from OkHttp request call.
     *
     * @property statusCode [StatusCode] is Hypertext Transfer Protocol (HTTP) response status codes.
     * @property headers The header fields of a single HTTP message.
     * @property raw The raw response from the HTTP client.
     * @property data The de-serialized response body of a successful data.
     */
    data class Success<T>(val response: Response<T>) : ApiResponse<T>() {
        val statusCode: StatusCode = getStatusCodeFromResponse(response)
        val headers: Headers = response.headers()
        val raw: okhttp3.Response = response.raw()
        val data: T by lazy { response.body() ?: throw NoContentException(statusCode.code) }
        override fun toString(): String = "[ApiResponse.Success](data=$data)"
    }

    /**
     * API response error case.
     * API communication conventions do not match or applications need to handle errors.
     * e.g., internal server error.
     *
     * @param response A response from OkHttp request call.
     *
     * @property statusCode [StatusCode] is Hypertext Transfer Protocol (HTTP) response status codes.
     * @property headers The header fields of a single HTTP message.
     * @property raw The raw response from the HTTP client.
     * @property errorBody The [ResponseBody] can be consumed only once.
     */
    data class Error<T>(val response: Response<T>?, val exception: Throwable?) : ApiResponse<T>() {
        val statusCode: StatusCode = getStatusCodeFromResponse(response)
        val errorBody: ResponseBody? = response?.errorBody()
        val message: String = exception?.localizedMessage ?: response?.errorBody()?.string() ?: ""
        override fun toString(): String =
            "[ApiResponse.Failure.Error-$statusCode](errorResponse=$response)(message=$message)"
    }

    companion object {
        /**
         * [Error] factory function. Only receives [Throwable] as an argument.
         *
         * @param ex A throwable.
         *
         * @return A [ApiResponse.Failure.Exception] based on the throwable.
         */
        fun <T> error(ex: Throwable): Error<T> = Error(response = null, exception = ex)

        /**
         * ApiResponse Factory.
         *
         * @param successCodeRange A success code range for determining the response is successful or failure.
         * @param [f] Create [ApiResponse] from [retrofit2.Response] returning from the block.
         * If [retrofit2.Response] has no errors, it creates [ApiResponse.Success].
         * If [retrofit2.Response] has errors, it creates [ApiResponse.Failure.Error].
         * If [retrofit2.Response] has occurred exceptions, it creates [ApiResponse.Failure.Exception].
         *
         * @return An [ApiResponse] model which holds information about the response.
         */
        @JvmSynthetic
        inline fun <T> of(
            successCodeRange: IntRange = 200..299,
            crossinline f: () -> Response<T>
        ): ApiResponse<T> = try {
            val response = f()
            if (response.raw().code in successCodeRange) {
                Success(response)
            } else {
                Error(response = response, exception = null)
            }
        } catch (ex: Exception) {
            Error(response = null, exception = ex)
        }

        /**
         * Returns a status code from the [Response].
         *
         * @param response A network callback response.
         *
         * @return A [StatusCode] from the network callback response.
         */
        fun <T> getStatusCodeFromResponse(response: Response<T>?): StatusCode {
            return StatusCode.values().find { it.code == response?.code() }
                ?: StatusCode.Unknown
        }
    }
}
