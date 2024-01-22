package com.thuanpx.view_mvvm_architecture.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by ThuanPx on 8/8/20.
 */

@JsonClass(generateAdapter = true)
class BaseResponse<T>(
    @Json(name = "count") val count: Int? = null,
    @Json(name = "results") val data: T?
)
