package com.thuanpx.mvvm_architecture.app

import android.app.Application
import com.thuanpx.mvvm_architecture.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by ThuanPx on 8/7/20.
 */

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        configTimber()
    }

    private fun configTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
