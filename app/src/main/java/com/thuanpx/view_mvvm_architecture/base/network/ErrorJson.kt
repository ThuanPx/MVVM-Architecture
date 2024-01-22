package com.thuanpx.view_mvvm_architecture.base.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorJson(
    @Json(name = "message") val message: String? = null,
    val error: String? = null,
    @Json(name = "status") val status: String? = null,
    val error_code: String? = null,
)