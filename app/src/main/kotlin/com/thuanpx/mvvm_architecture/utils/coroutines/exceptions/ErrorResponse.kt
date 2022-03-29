package com.thuanpx.mvvm_architecture.utils.coroutines.exceptions

/**
 * A customized pokemon error response.
 *
 * @param code A network response code.
 * @param message A network error message.
 */
data class ErrorResponse constructor(
    val code: Int,
    override val message: String?
) : Throwable(message)
