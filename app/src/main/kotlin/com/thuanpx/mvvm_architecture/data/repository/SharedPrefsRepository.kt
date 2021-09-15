package com.thuanpx.mvvm_architecture.data.repository

import com.thuanpx.mvvm_architecture.data.local.sharedpfers.SharedPrefs
import javax.inject.Inject

interface SharedPrefsRepository {
    fun clear()
}

class DefaultSharedPrefsRepository
@Inject constructor(private val sharedPrefs: SharedPrefs) : SharedPrefsRepository {

    companion object {
    }

    override fun clear() {
        sharedPrefs.clear()
    }

}
