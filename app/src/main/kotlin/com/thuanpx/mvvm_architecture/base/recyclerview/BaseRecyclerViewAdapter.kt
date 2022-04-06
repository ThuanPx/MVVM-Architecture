package com.thuanpx.mvvm_architecture.base.recyclerview

import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ThuanPx on 8/5/20.
 */

abstract class BaseRecyclerViewAdapter<T, V : RecyclerView.ViewHolder>(
    private var dataList: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<V>() {

    protected var itemClickListener: ((T) -> Unit)? = null

    private var handler = Handler(Looper.getMainLooper())

    override fun getItemCount(): Int {
        return dataList.size
    }

    open fun getItem(position: Int): T? {
        return if (position < 0 || position >= dataList.size) {
            null
        } else dataList[position]
    }

    fun getData(): MutableList<T> {
        return dataList
    }

    fun updateData(newData: MutableList<T>?, diffUtilCallback: DiffUtil.Callback) {
        handler.post {
            newData?.let {
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                dataList = it
                diffResult.dispatchUpdatesTo(this)
            }
        }
    }

    fun addData(newData: MutableList<T>?) {
        handler.post {
            newData?.let {
                dataList.addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    fun replaceData(newData: MutableList<T>?) {
        handler.post {
            newData?.let {
                dataList = it
                notifyDataSetChanged()
            }
        }
    }

    fun clearData(isNotify: Boolean = true) {
        dataList.clear()
        if (isNotify) notifyDataSetChanged()
    }

    fun addItem(data: T, position: Int) {
        dataList.add(position, data)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int, isNotifyAll: Boolean = false) {
        if (position < 0 || position >= dataList.size) {
            return
        }
        dataList.removeAt(position)
        if (isNotifyAll) notifyDataSetChanged() else notifyItemChanged(position)
    }

    fun replaceItem(item: T, position: Int, isNotifyAll: Boolean = false) {
        if (position < 0 || position >= dataList.size) {
            return
        }
        dataList[position] = item
        if (isNotifyAll) notifyDataSetChanged() else notifyItemChanged(position)
    }

    fun registerItemClickListener(onItemClickListener: (T) -> Unit) {
        itemClickListener = onItemClickListener
    }

    fun unRegisterItemClickListener() {
        itemClickListener = null
    }
}
