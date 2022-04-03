package com.thuanpx.mvvm_architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelLazy
import androidx.viewbinding.ViewBinding
import com.thuanpx.ktext.boolean.isNotTrue
import com.thuanpx.ktext.boolean.isTrue
import com.thuanpx.ktext.widget.dialog
import com.thuanpx.mvvm_architecture.R
import com.thuanpx.mvvm_architecture.base.viewmodel.BaseViewModel
import com.thuanpx.mvvm_architecture.utils.coroutines.exceptions.ErrorResponse
import com.thuanpx.mvvm_architecture.widget.ProgressDialog
import org.json.JSONObject
import java.net.HttpURLConnection
import kotlin.reflect.KClass

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 *
 * @viewModel -> view model
 * @viewModelClass -> class view model
 * @viewBinding -> class binding
 * @initialize -> init UI, adapter, listener...
 * @onSubscribeObserver -> subscribe observer live data
 *
 */

abstract class BaseActivity<viewModel : BaseViewModel, viewBinding : ViewBinding>(viewModelClass: KClass<viewModel>) :
    AppCompatActivity() {

    protected val viewModel by ViewModelLazy(
        viewModelClass,
        { viewModelStore },
        { defaultViewModelProviderFactory })
    protected lateinit var viewBinding: viewBinding
    abstract fun inflateViewBinding(inflater: LayoutInflater): viewBinding

    protected var progressDialog: ProgressDialog? = null

    protected abstract fun initialize()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = inflateViewBinding(layoutInflater)
        progressDialog = ProgressDialog(this)
        setContentView(viewBinding.root)
        initialize()
        onSubscribeObserver()
    }

    fun showLoading(isShow: Boolean) {
        if (isShow && progressDialog?.isShowing.isNotTrue()) {
            progressDialog?.show()
        } else if (progressDialog?.isShowing.isTrue()) {
            progressDialog?.dismiss()
        }
    }

    open fun onSubscribeObserver() {
        viewModel.run {
            isLoading.observe(this@BaseActivity) {
                showLoading(it)
            }
            exception.observe(this@BaseActivity) {
                handleApiError(it)
            }
        }
    }

    fun handleApiError(throwable: Throwable) {
        (throwable as? ErrorResponse)?.let { errorResponse ->
            getErrorMessage(errorResponse)?.let {
                dialog {
                    message = it
                }
            }
        }
    }

    private fun getErrorMessage(errorResponse: ErrorResponse): String? {
        if (errorResponse.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            // TODO login
            return null
        }
        return errorResponse.message?.let {
            try {
                // Handle get message error when request api, depend on format json api
                val jsonObject = JSONObject(it)
                val message = jsonObject.getString("error")
                if (!message.isNullOrBlank()) {
                    message
                } else {
                    getString(R.string.msg_error_data_parse)
                }
            } catch (ex: Exception) {
                getString(R.string.msg_error_data_parse)
            }
        } ?: kotlin.run {
            getString(R.string.msg_error_data_parse)
        }
    }

    /**
     * Old base
     */

//    fun handleDefaultApiError(apiError: Throwable) {
//        when (apiError) {
//            is HttpException -> {
//                getErrorMessage(apiError)?.let {
//                    dialog {
//                        message = it
//                    }
//                }
//            }
//            is SocketTimeoutException -> {
//                dialog {
//                    message = getString(R.string.msg_error_time_out)
//                }
//            }
//            is IOException -> {
//                dialog {
//                    message = getString(R.string.msg_error_no_internet)
//                }
//            }
//            else -> {
//                dialog {
//                    message = getString(R.string.msg_error_data_parse)
//                }
//            }
//        }
//    }
//
//    private fun getErrorMessage(e: Exception): String? {
//        val responseBody = (e as HttpException).response()?.errorBody()
//        val errorCode = e.response()?.code()
//        if (errorCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
//            // TODO reLogin
//        }
//
//        return responseBody?.let {
//            try {
//                // Handle get message error when request api, depend on format json api
//                val jsonObject = JSONObject(responseBody.string())
//                val message = jsonObject.getString("message")
//                if (!message.isNullOrBlank()) {
//                    message
//                } else {
//                    getString(R.string.msg_error_data_parse)
//                }
//            } catch (ex: Exception) {
//                e.message
//            }
//        } ?: kotlin.run {
//            getString(R.string.msg_error_data_parse)
//        }
//    }
}
