package com.thuanpx.mvvm_architecture.utils.coroutines.exceptions

/**
 * Copyright © 2022 Est Rouge VN.
 * Created by ThuanPx on 3/29/22.
 */
class UnknownException constructor(
    val code: Int,
    override val message: String? =
        "The server has successfully fulfilled the request with the code ($code) and that there is " +
            "no additional content to send in the response payload body."
) : Throwable(message)
