package com.thuanpx.mvvm_architecture.model.response

import com.google.gson.annotations.SerializedName

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/8/20.
 */

class BaseResponse<T>(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val data: T
)
