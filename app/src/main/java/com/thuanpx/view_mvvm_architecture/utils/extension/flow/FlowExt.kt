package com.thuanpx.view_mvvm_architecture.utils.extension.flow

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * Created by ThuanPx on 4/2/22.
 */

fun <T> Flow<T>.loading(isLoading: MutableLiveData<Boolean>): Flow<T> {
    return onStart { isLoading.postValue(true) }
        .onCompletion { isLoading.postValue(false) }
}
