package com.thuanpx.mvvm_architecture.utils.extension

import com.thuanpx.mvvm_architecture.utils.liveData.SingleLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * Copyright © 2022 Est Rouge VN.
 * Created by ThuanPx on 3/29/22.
 */


fun <T> Flow<T>.loading(isLoading: SingleLiveData<Boolean>): Flow<T> {
    return onStart { isLoading.postValue(true) }
        .onCompletion { isLoading.postValue(false) }
}