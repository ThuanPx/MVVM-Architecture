package com.thuanpx.view_mvvm_architecture.utils.extension.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

inline fun <reified T : Any> T.toJson(
    moshi: Moshi
): String = moshi
    .adapter(T::class.java).toJson(this)


inline fun <reified T : Any> String.fromJson(
    moshi: Moshi
): T? = moshi
    .adapter(T::class.java).fromJson(this)


inline fun <reified T : Any> String.toJsonObjectList(
    factory: JsonAdapter.Factory,
    customBuilder: Moshi.Builder = Moshi.Builder()
): List<T>? {
    return Types.newParameterizedType(List::class.java, T::class.java).let { type ->
        customBuilder
            .add(factory).build()
            .adapter<List<T>>(type).fromJson(this)
    }
}


inline fun <reified T : Any> String.toJsonObjectArrayList(
    factory: JsonAdapter.Factory,
    customBuilder: Moshi.Builder = Moshi.Builder()
): ArrayList<T>? {
    return Types.newParameterizedType(ArrayList::class.java, T::class.java).let { type ->
        customBuilder
            .add(factory).build()
            .adapter<ArrayList<T>>(type).fromJson(this)
    }
}


inline fun <reified T : Any> String.toJsonObjectMutableList(
    moshi: Moshi
): MutableList<T>? {
    return Types.newParameterizedType(MutableList::class.java, T::class.java).let { type ->
        moshi.adapter<MutableList<T>>(type).fromJson(this)
    }
}
