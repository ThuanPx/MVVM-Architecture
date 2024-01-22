package com.thuanpx.view_mvvm_architecture.base.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

/**
 * Created by ThuanPx on 8/5/20.
 */
abstract class BaseViewModel : ViewModel() {

    private var loadingCount: Int = 0
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    protected val _error = MutableSharedFlow<Throwable>()
    val error: SharedFlow<Throwable>
        get() = _error

    /**
     * To show loading manually, should call `hideLoading` after
     */
    protected fun showLoading() {
        if (loadingCount == 0) {
            _isLoading.value = true
        }
        loadingCount++
    }

    /**
     * To hide loading manually, should be called after `showLoading`
     */
    protected fun hideLoading() {
        loadingCount--
        if (loadingCount == 0) {
            _isLoading.value = false
        }
    }

    protected suspend inline fun <T> Flow<T>.async(
        isRefresh: MutableStateFlow<Boolean>,
        crossinline action: suspend (T) -> Unit
    ) {
        this.onStart { isRefresh.emit(true) }
            .onCompletion { isRefresh.emit(false) }
            .catch { _error.emit(it) }
            .collect {
                action.invoke(it)
            }
    }

    protected suspend inline infix fun <T> Flow<T>.async(
        crossinline action: suspend (T) -> Unit
    ) {
        this.onStart { showLoading() }
            .onCompletion { hideLoading() }
            .catch { _error.emit(it) }
            .collect {
                action.invoke(it)
            }
    }

    protected suspend inline fun <T> Flow<T>.async() {
        this.onStart { showLoading() }
            .onCompletion { hideLoading() }
            .catch { _error.emit(it) }
            .collect {}
    }
}
