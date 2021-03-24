package com.thuanpx.mvvm_architecture.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.thuanpx.mvvm_architecture.utils.extension.clicks
import com.thuanpx.mvvm_architecture.utils.extension.inflater

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/15/20.
 */

abstract class BaseListAdapter<T, VH : RecyclerView.ViewHolder, VB : ViewBinding>(
    private val viewHolder: Class<VH>,
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    areItemsTheSameCallback: (old: T, new: T) -> Boolean? = { _, _ -> null },
    areContentsTheSameCallback: (old: T, new: T) -> Boolean? = { _, _ -> null }
) :
    ListAdapter<T, VH>(GenericDiffUtil(areItemsTheSameCallback, areContentsTheSameCallback)) {
    abstract fun bindItems(item: T, holder: VH, position: Int, itemCount: Int)

    var onItemClickListener: ((T, Int) -> Unit)? = null

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item: T = getItem(holder.adapterPosition)
        bindItems(item, holder, position, itemCount)
    }

    /**
     * Use proguard rule as the following
     * -keep public class mypackagename.ViewHolder { public <init>(...); }
     * or annotate it with the @Keep method from androidX
     * @param parent ViewGroup
     * @param viewType Int
     * @return VH
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = bindingInflater.invoke(parent.inflater, parent, false)
        val holder = setViewHolder(binding)

        holder.itemView.clicks {
            if (holder.adapterPosition != RecyclerView.NO_POSITION)
                onItemClickListener?.invoke(
                    getItem(holder.adapterPosition),
                    holder.adapterPosition
                )
        }
        return holder
    }

    @Suppress("UNCHECKED_CAST")
    private fun setViewHolder(binding: ViewBinding): VH =
        viewHolder.declaredConstructors.first().newInstance(binding) as VH
}
