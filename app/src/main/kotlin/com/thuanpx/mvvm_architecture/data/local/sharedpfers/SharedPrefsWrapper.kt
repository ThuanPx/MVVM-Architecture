package com.thuanpx.mvvm_architecture.data.local.sharedpfers

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import com.google.gson.Gson

class SharedPrefsWrapper(val sharedPrefs: SharedPreferences, val gson: Gson) {

    fun <T> set(key: String, value: T, commitNow: Boolean = false) {
        sharedPrefs.edit(commitNow) {
            when (value) {
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                else -> putString(key, gson.toJson(value))
            }
        }
    }

    operator fun <T> set(key: String, value: T) {
        set(key, value, false)
    }

    inline operator fun <reified T> get(key: String): T {
        return when (T::class) {
            String::class -> sharedPrefs.getString(key, "") as T
            Boolean::class -> sharedPrefs.getBoolean(key, false) as T
            Float::class -> sharedPrefs.getFloat(key, 0f) as T
            Int::class -> sharedPrefs.getInt(key, 0) as T
            Long::class -> sharedPrefs.getLong(key, 0L) as T
            else -> gson.fromJson(sharedPrefs.getString(key, ""), T::class.java)
        }
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> getLive(key: String): LiveData<T> {
        return when (T::class) {
            String::class -> sharedPrefs.stringLiveData(key, "")
            Boolean::class -> sharedPrefs.booleanLiveData(key, false)
            Float::class -> sharedPrefs.floatLiveData(key, 0f)
            Int::class -> sharedPrefs.intLiveData(key, 0)
            Long::class -> sharedPrefs.longLiveData(key, 0L)
            else -> sharedPrefs.jsonLiveData(key, gson, T::class.java)
        } as LiveData<T>
    }
}
