package com.thuanpx.mvvm_architecture.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thuanpx.mvvm_architecture.model.exception.ApiException
import com.thuanpx.mvvm_architecture.utils.DataResult
import com.thuanpx.mvvm_architecture.utils.liveData.SingleLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */
abstract class BaseViewModel : ViewModel() {

    val isLoading = SingleLiveData<Boolean>()
    val errorMessage = SingleLiveData<String>()
    val reLogin = SingleLiveData<Unit>()

    private var loadingCount = 0

    /**
     * Calls api with view model scope
     */
    protected fun <T> viewModelScope(
        liveData: MutableLiveData<T>,
        isShowLoading: Boolean = true,
        onRequest: suspend CoroutineScope.() -> DataResult<T>,
        onSuccess: ((T) -> Unit)? = null,
        onError: (Exception) -> String? = { e -> e.message }
    ) {
        viewModelScope.launch {
            showLoading(isShowLoading)
            when (val asynchronousTasks = onRequest(this)) {
                is DataResult.Success -> {
                    onSuccess?.invoke(asynchronousTasks.data) ?: kotlin.run {
                        liveData.value = asynchronousTasks.data
                    }
                }
                is DataResult.Error -> onError(asynchronousTasks.exception)?.let {
                    if (asynchronousTasks.exception is ApiException && asynchronousTasks.exception.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        reLogin.value = Unit
                    }
                    errorMessage.value = it
                }
            }
            hideLoading(isShowLoading)
        }
    }

    protected fun <T> viewModelScope(
        liveData: MutableLiveData<T>,
        isShowLoading: Boolean = true,
        onRequest: suspend CoroutineScope.() -> DataResult<T>,
        onSuccess: ((T) -> Unit)? = null
    ) {
        viewModelScope(liveData, isShowLoading, onRequest, onSuccess) { it.message }
    }

    protected fun showLoading(isShowLoading: Boolean) {
        if (!isShowLoading) return
        loadingCount++
        if (isLoading.value != true) isLoading.value = true
    }

    protected fun hideLoading(isShowLoading: Boolean) {
        if (!isShowLoading) return
        loadingCount--
        if (loadingCount <= 0) {
            // reset loadingCount
            loadingCount = 0
            isLoading.value = false
        }
    }
}
