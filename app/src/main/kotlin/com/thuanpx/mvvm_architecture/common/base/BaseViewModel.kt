package com.thuanpx.mvvm_architecture.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thuanpx.mvvm_architecture.utils.DataResult
import com.thuanpx.mvvm_architecture.utils.liveData.SingleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */
abstract class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<SingleEvent<Boolean>>()
    val errorMessage = MutableLiveData<SingleEvent<String>>()

    private var loadingCount = 0

    /**
     * Calls api with view model scope
     */
    protected fun <T> withResultViewModelScope(
        liveData: MutableLiveData<T>,
        isShowLoading: Boolean = true,
        onRequest: suspend CoroutineScope.() -> DataResult<T>,
        onSuccess: (() -> Unit)? = null,
        onError: (Exception) -> String? = { e -> e.message }
    ) {
        viewModelScope.launch {
            showLoading(isShowLoading)
            when (val asynchronousTasks = onRequest(this)) {
                is DataResult.Success -> {
                    onSuccess?.invoke()
                    liveData.value = asynchronousTasks.data
                }
                is DataResult.Error -> onError(asynchronousTasks.exception)?.let {
                    errorMessage.value = SingleEvent(it)
                }
            }
            hideLoading(isShowLoading)
        }
    }

    protected fun <T> withResultViewModelScope(
        liveData: MutableLiveData<T>,
        isShowLoading: Boolean = true,
        onRequest: suspend CoroutineScope.() -> DataResult<T>,
        onSuccess: (() -> Unit)? = null
    ) {
        withResultViewModelScope(liveData, isShowLoading, onRequest, onSuccess) { it.message }
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
            isLoading.value = SingleEvent(true)
        }
    }
}
