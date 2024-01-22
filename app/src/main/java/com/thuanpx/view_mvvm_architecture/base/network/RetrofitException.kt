package com.thuanpx.view_mvvm_architecture.base.network

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class RetrofitException(
    /** The request URL which produced the error.  */
        val url: String?,
    /** Response object containing status code, headers, body, etc.  */
        val response: Response<Throwable>?,
    /** The event kind which triggered this error.  */
        val kind: Kind,
    val exception: Throwable?
) : RuntimeException(exception) {

    /** Identifies the event kind which triggered a [RetrofitException].  */
    enum class Kind {
        /** An [IOException] occurred while communicating to the server.  */
        NETWORK,
        /** User login requirement **/
        NO_AUTHENTICATOR,
        /** A non-200 HTTP status code was received from the server.  */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    companion object {

        fun authenticatorError(): RetrofitException {
            return RetrofitException(null, null, Kind.NO_AUTHENTICATOR, null)
        }

        fun httpError(url: String, response: Response<Throwable>, throwable: HttpException): RetrofitException {
            return RetrofitException(url, response, Kind.HTTP, throwable)
        }

        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(null, null, Kind.NETWORK, exception)
        }

        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(null, null, Kind.UNEXPECTED, exception)
        }
    }
}