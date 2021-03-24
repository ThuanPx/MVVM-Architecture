package com.thuanpx.mvvm_architecture.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/15/20.
 */
/**
 * Use [T] as data class preferably if you don't pass anything in constructors
 * @param T
 * @property areItemsTheSameCallback Function2<[@kotlin.ParameterName] T, [@kotlin.ParameterName] T, Boolean?>
 * @property areContentsTheSameCallback Function2<[@kotlin.ParameterName] T, [@kotlin.ParameterName] T, Boolean?>
 * @constructor
 */
class GenericDiffUtil<T>(
    private val areItemsTheSameCallback: (old: T, new: T) -> Boolean?,
    private val areContentsTheSameCallback: (old: T, new: T) -> Boolean?
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = areItemsTheSameCallback(oldItem, newItem) ?: newItem == oldItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = areContentsTheSameCallback(oldItem, newItem) ?: newItem == oldItem
}
