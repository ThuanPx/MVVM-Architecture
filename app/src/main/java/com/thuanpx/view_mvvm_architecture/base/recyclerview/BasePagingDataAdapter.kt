package com.thuanpx.view_mvvm_architecture.base.recyclerview

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ThuanPx on 04/02/2023.
 */
abstract class BasePagingDataAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    diffUtil: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, VH>(diffUtil) {

    fun getItems() = snapshot().toMutableList()

    fun updateItem(index: Int, item: T, onUpdateSuccess: (() -> Unit)? = null) {
        val items = snapshot().toMutableList()
        items[index] = item
        notifyItemChanged(index) {
            onUpdateSuccess?.invoke()
        }
    }

}
