package com.thuanpx.mvvm_architecture.base.viewmodel

import androidx.lifecycle.ViewModel
import com.thuanpx.mvvm_architecture.utils.liveData.SingleLiveData

/**
 * Created by ThuanPx on 8/5/20.
 */
abstract class BaseViewModel : ViewModel() {

    val isLoading = SingleLiveData<Boolean>()
    val exception = SingleLiveData<Throwable>()

    private var loadingCount = 0

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
