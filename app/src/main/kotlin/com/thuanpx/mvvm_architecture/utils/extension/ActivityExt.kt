package com.thuanpx.mvvm_architecture.utils.extension

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/10/20.
 */

/**
 * Launches a new coroutine and repeats `block` every time the Activity's viewLifecycleOwner
 * is in and out of `minActiveState` lifecycle state.
 * Source: https://medium.com/androiddevelopers/repeatonlifecycle-api-design-story-8670d1a7d333
 */
inline fun FragmentActivity.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    this.lifecycleScope.launch {
        this@launchAndRepeatWithViewLifecycle.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}