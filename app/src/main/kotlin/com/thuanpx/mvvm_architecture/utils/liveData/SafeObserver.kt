package com.thuanpx.mvvm_architecture.utils.liveData

import androidx.lifecycle.Observer

/**
 * Created by Hyperion on 15/12/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class SafeObserver<T>(private val notifier: (T) -> Unit) : Observer<T> {
    override fun onChanged(t: T?) {
        t?.let { notifier(t) }
    }
}
