package com.thuanpx.mvvm_architecture.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by ThuanPx on 8/8/20.
 */

class BaseResponse<T>(
    @SerializedName("count") val count: Int? = null,
    @SerializedName("results") val data: T
)
