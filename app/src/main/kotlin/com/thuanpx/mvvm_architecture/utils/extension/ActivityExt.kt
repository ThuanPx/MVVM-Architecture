package com.thuanpx.mvvm_architecture.utils.extension

import com.thuanpx.mvvm_architecture.R
import com.thuanpx.mvvm_architecture.common.base.BaseActivity
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/10/20.
 */

fun BaseActivity<*, *>?.handleDefaultApiError(apiError: Exception) {
    this?.let {
        when (apiError) {
            is HttpException -> {
                getErrorMessage(apiError)?.let {
                    showAlertDialog(message = it)
                }
            }
            is SocketTimeoutException -> {
                showAlertDialog(message = getString(R.string.msg_error_time_out))
            }
            is IOException -> {
                showAlertDialog(message = getString(R.string.msg_error_no_internet))
            }
            else -> {
                showAlertDialog(message = getString(R.string.msg_error_data_parse))
            }
        }
    }
}

fun BaseActivity<*, *>.getErrorMessage(e: Exception): String? {
    val responseBody = (e as HttpException).response()?.errorBody()
    val errorCode = e.response()?.code()
    if (errorCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
        // TODO reLogin
    }

    return responseBody?.let {
        try {
            // Handle get message error when request api, depend on format json api
            val jsonObject = JSONObject(responseBody.string())
            val message = jsonObject.getString("message")
            if (!message.isNullOrBlank()) {
                message
            } else {
                getString(R.string.msg_error_data_parse)
            }
        } catch (ex: Exception) {
            e.message
        }
    } ?: kotlin.run {
        getString(R.string.msg_error_data_parse)
    }
}