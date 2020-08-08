package com.thuanpx.mvvm_architecture.model.exception

import com.google.gson.annotations.SerializedName

class ApiException(val code: Int, val error: ApiError) : RuntimeException() {

    override val message: String? by lazy {
        return@lazy ("$code: ${error.message}")
    }

    /**
     * Sample error with single property is message
     */
    data class ApiError(
        @SerializedName("message")
        val message: String
    )
}
