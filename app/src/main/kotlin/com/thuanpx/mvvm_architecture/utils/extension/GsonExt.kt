package com.thuanpx.mvvm_architecture.utils.extension

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Copyright © 2021 Neolab VN.
 * Created by ThuanPx on 24/12/2021.
 */

inline fun <reified T> Gson.fromJsonType(json: String): T = fromJson(json, object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.toJsonType(obj: T): String = toJson(obj)

inline fun <reified T> T.clone(gson: Gson = Gson()): T {
    return gson.fromJsonType(json = gson.toJsonType(this))
}