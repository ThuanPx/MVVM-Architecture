package com.thuanpx.mvvm_architecture.base

import androidx.recyclerview.widget.DiffUtil

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */

open class BaseDiffUtil<T>(var oldList: MutableList<T> = mutableListOf(), var newList: MutableList<T> = mutableListOf()) : DiffUtil.Callback() {

    private var areContentsTheSame: ((T, T) -> Boolean)? = null
    private var areItemsTheSame: ((T, T) -> Boolean)? = null

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        areItemsTheSame?.invoke(oldList[oldItemPosition], newList[newItemPosition]) ?: (oldList[oldItemPosition] == newList[newItemPosition])

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        areContentsTheSame?.invoke(oldList[oldItemPosition], newList[newItemPosition]) ?: (oldList[oldItemPosition] == newList[newItemPosition])

    fun setData(old: MutableList<T>, new: MutableList<T>) {
        this.oldList = old
        this.newList = new
    }

    fun areContentsTheSame(function: (old: T, new: T) -> Boolean) {
        areContentsTheSame = function
    }

    fun areItemsTheSame(function: (old: T, new: T) -> Boolean) {
        areItemsTheSame = function
    }
}
