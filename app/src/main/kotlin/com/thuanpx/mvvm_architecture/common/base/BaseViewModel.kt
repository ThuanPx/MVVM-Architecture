package com.thuanpx.mvvm_architecture.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thuanpx.mvvm_architecture.model.exception.ApiException
import com.thuanpx.mvvm_architecture.utils.DataResult
import com.thuanpx.mvvm_architecture.utils.liveData.SingleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */
abstract class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<SingleEvent<Boolean>>()
    val errorMessage = MutableLiveData<SingleEvent<String>>()
    val reLogin = MutableLiveData<SingleEvent<Unit>>()

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
                        reLogin.value = SingleEvent(Unit)
                    }
                    errorMessage.value = SingleEvent(it)
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
        if (isLoading.value?.peekContent() != true) isLoading.value = SingleEvent(true)
    }

    protected fun hideLoading(isShowLoading: Boolean) {
        if (!isShowLoading) return
        loadingCount--
        if (loadingCount <= 0) {
            // reset loadingCount
            loadingCount = 0
            isLoading.value = SingleEvent(false)
        }
    }
}
