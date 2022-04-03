package com.thuanpx.mvvm_architecture.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thuanpx.mvvm_architecture.utils.DataResult
import com.thuanpx.mvvm_architecture.utils.liveData.SingleLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */
abstract class BaseViewModel : ViewModel() {

    val isLoading = SingleLiveData<Boolean>()
    val exception = SingleLiveData<Throwable>()

    private var loadingCount = 0

    /**
     * Old base
     */

    /**
     * Calls api with view model scope
     */
    protected fun <T> viewModelScope(
        liveData: MutableLiveData<T>,
        isShowLoading: Boolean = true,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null,
        onRequest: suspend CoroutineScope.() -> DataResult<T>
    ): Job {
        return viewModelScope.launch {
            if (isShowLoading) showLoading()
            when (val asynchronousTasks = onRequest(this)) {
                is DataResult.Success -> {
                    onSuccess?.invoke(asynchronousTasks.data) ?: kotlin.run {
                        asynchronousTasks.data?.let {
                            liveData.value = it
                        }
                    }
                }
                is DataResult.Error -> {
                    onError?.invoke(asynchronousTasks.exception) ?: kotlin.run {
                        exception.value = asynchronousTasks.exception
                    }
                }
            }
            if (isShowLoading) hideLoading()
        }
    }

    protected fun <T> viewModelScope(
        isShowLoading: Boolean = true,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null,
        onRequest: suspend CoroutineScope.() -> DataResult<T>
    ): Job {
        return viewModelScope.launch {
            if (isShowLoading) showLoading()
            when (val asynchronousTasks = onRequest(this)) {
                is DataResult.Success -> {
                    onSuccess?.invoke(asynchronousTasks.data)
                }
                is DataResult.Error -> {
                    onError?.invoke(asynchronousTasks.exception) ?: kotlin.run {
                        exception.value = asynchronousTasks.exception
                    }
                }
            }
            if (isShowLoading) hideLoading()
        }
    }

    protected fun viewModelScope(
        isShowLoading: Boolean = true,
        onSuccess: (() -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null,
        onRequest: suspend CoroutineScope.() -> DataResult<Any>
    ): Job {
        return viewModelScope.launch {
            if (isShowLoading) showLoading()
            when (val asynchronousTasks = onRequest(this)) {
                is DataResult.Success -> {
                    onSuccess?.invoke()
                }
                is DataResult.Error -> {
                    onError?.invoke(asynchronousTasks.exception) ?: kotlin.run {
                        exception.value = asynchronousTasks.exception
                    }
                }
            }
            if (isShowLoading) hideLoading()
        }
    }

    /**
     * Old base
     */

    protected fun showLoading() {
        loadingCount++
        if (isLoading.value != true) isLoading.value = true
    }

    protected fun hideLoading() {
        loadingCount--
        if (loadingCount <= 0) {
            // reset loadingCount
            loadingCount = 0
            isLoading.value = false
        }
    }
}
