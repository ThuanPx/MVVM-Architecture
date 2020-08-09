package com.thuanpx.mvvm_architecture.utils.extension

import android.view.View
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/8/20.
 */

@ExperimentalCoroutinesApi
fun View.clicks() = callbackFlow {
    setOnClickListener { offer(Unit) }
    awaitClose { setOnClickListener(null) }
}
