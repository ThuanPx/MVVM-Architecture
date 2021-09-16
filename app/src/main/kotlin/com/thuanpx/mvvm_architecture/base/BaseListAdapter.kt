package com.thuanpx.mvvm_architecture.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Copyright © 2021 Neolab VN.
 * Created by ThuanPx on 10/08/2021.
 */
abstract class BaseListAdapter<T, VH : RecyclerView.ViewHolder>(
    diffUtil: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffUtil) {

    internal var itemClickListener: ((T, Int) -> Unit)? = null

    fun unRegisterItemClickListener() {
        itemClickListener = null
    }

    fun addItem(index: Int, item: T) {
        val currentList = currentList.toMutableList()
        currentList.add(index, item)
        submitList(currentList)
    }

    fun updateItems(items: List<T>?, listener: (() -> Unit)? = null) {
        if (items.isNullOrEmpty()) return
        val currentList = currentList.toMutableList()
        currentList.addAll(items)
        submitList(currentList) {
            listener?.invoke()
        }
    }

    fun removeItem(index: Int) {
        val currentList = currentList.toMutableList()
        currentList.removeAt(index)
        submitList(currentList)
    }

}
