package com.thuanpx.mvvm_architecture.utils.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.thuanpx.mvvm_architecture.base.BaseListAdapter

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/15/20.
 */

/**
 * private val generatedAdapter by lazy {
 * generateRecycler(
TestViewHolder::class.java,
ItemViewBinding::inflater,
{ old: TestModel, new: TestModel -> old.name == new.name }) { item, holder, _, _->
holder.onBind(item)
}
}
 * make sure you have added a proguard rule see [AbstractListAdapter.onCreateViewHolder]
 * @param layout Int the layout res id
 * @param viewHolder Class<VH> this one is used for reflection to instantiate the [RecyclerView.ViewHolder]
 * @param areItemsTheSameCallback callback invocation as a function parameter that returns Boolean as a condition whether items are the same
 * @param areContentsTheSameCallback callback invocation as a function parameter that returns Boolean as a condition whether contents of the items are the same
 * @param binder just as you would call [RecyclerView.Adapter.onBindViewHolder]
 * @return AbstractListAdapter<T, VH>
 */

inline fun <reified T, VH : RecyclerView.ViewHolder, VB : ViewBinding> generateListAdapter(
    viewHolder: Class<VH>,
    noinline bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    noinline areItemsTheSameCallback: (old: T, new: T) -> Boolean? = { _, _ -> null },
    noinline areContentsTheSameCallback: (old: T, new: T) -> Boolean? = { _, _ -> null },
    crossinline binder: (item: T, holder: VH, position: Int, itemCount: Int) -> Unit
): BaseListAdapter<T, VH, VB> {

    return object : BaseListAdapter<T, VH, VB>(
        viewHolder,
        bindingInflater,
        areItemsTheSameCallback,
        areContentsTheSameCallback
    ) {
        override fun bindItems(item: T, holder: VH, position: Int, itemCount: Int) {
            binder(item, holder, position, itemCount)
        }
    }
}

/**
 * private val generateVerticalListAdapter by lazy {
activityMainBinding.recycler.generateVerticalAdapter(
TestViewHolder::class.java,
ItemViewBinding::inflater,
{ old: TestModel, new: TestModel -> old.name == new.name }) { item, holder, _, _->
holder.onBind(item)
}
}
 * make sure you have added a proguard rule see [AbstractListAdapter.onCreateViewHolder]
 * @receiver RecyclerView
 * @param layout Int the layout res id
 * @param viewHolder Class<VH> this one is used for reflection to instantiate the [RecyclerView.ViewHolder]
 * @param areItemsTheSameCallback callback invocation as a function parameter that returns Boolean as a condition whether items are the same
 * @param areContentsTheSameCallback callback invocation as a function parameter that returns Boolean as a condition whether contents of the items are the same
 * @param binder just as you would call [RecyclerView.Adapter.onBindViewHolder]
 * @return AbstractListAdapter<T, VH>
 */

inline fun <reified T, VH : RecyclerView.ViewHolder, VB : ViewBinding> RecyclerView.generateVerticalListAdapter(
    viewHolder: Class<VH>,
    noinline bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    noinline areItemsTheSameCallback: (old: T, new: T) -> Boolean? = { _, _ -> null },
    noinline areContentsTheSameCallback: (old: T, new: T) -> Boolean? = { _, _ -> null },
    crossinline binder: (item: T, holder: VH, position: Int, itemCount: Int) -> Unit
): BaseListAdapter<T, VH, VB> {

    val adapter = generateListAdapter(
        viewHolder,
        bindingInflater,
        areItemsTheSameCallback,
        areContentsTheSameCallback,
        binder
    )
    initRecyclerViewAdapter(adapter)
    return adapter
}

/**
 * private val generateVerticalListAdapter by lazy {
activityMainBinding.recycler.generateHorizontalAdapter(
TestViewHolder::class.java,
ItemViewBinding::inflater,
{ old: TestModel, new: TestModel -> old.name == new.name }) { item, holder, _, _->
holder.onBind(item)
}
}
 * make sure you have added a proguard rule see [AbstractListAdapter.onCreateViewHolder]
 * @receiver RecyclerView
 * @param layout Int the layout res id
 * @param viewHolder Class<VH> this one is used for reflection to instantiate the [RecyclerView.ViewHolder]
 * @param areItemsTheSameCallback callback invocation as a function parameter that returns Boolean as a condition whether items are the same
 * @param areContentsTheSameCallback callback invocation as a function parameter that returns Boolean as a condition whether contents of the items are the same
 * @param binder just as you would call [RecyclerView.Adapter.onBindViewHolder]
 * @return AbstractListAdapter<T, VH>
 */

inline fun <reified T, VH : RecyclerView.ViewHolder, VB : ViewBinding> RecyclerView.generateHorizontalAdapter(
    viewHolder: Class<VH>,
    noinline bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    noinline areItemsTheSameCallback: (old: T, new: T) -> Boolean? = { _, _ -> null },
    noinline areContentsTheSameCallback: (old: T, new: T) -> Boolean? = { _, _ -> null },
    crossinline binder: (item: T, holder: VH, position: Int, itemCount: Int) -> Unit
): BaseListAdapter<T, VH, VB> {

    val adapter = generateListAdapter(
        viewHolder,
        bindingInflater,
        areItemsTheSameCallback,
        areContentsTheSameCallback,
        binder
    )
    initRecyclerViewAdapter(adapter, RecyclerView.HORIZONTAL)
    return adapter
}

inline fun <reified T, VH : RecyclerView.ViewHolder, VB : ViewBinding> RecyclerView.generateGridLayoutAdapter(
    viewHolder: Class<VH>,
    spanCount: Int,
    noinline bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    noinline areItemsTheSameCallback: (old: T, new: T) -> Boolean? = { _, _ -> null },
    noinline areContentsTheSameCallback: (old: T, new: T) -> Boolean? = { _, _ -> null },
    crossinline binder: (item: T, holder: VH, position: Int, itemCount: Int) -> Unit
): BaseListAdapter<T, VH, VB> {

    val adapter = generateListAdapter(
        viewHolder,
        bindingInflater,
        areItemsTheSameCallback,
        areContentsTheSameCallback,
        binder
    )
    val layoutManager = GridLayoutManager(context, spanCount)
    initRecyclerViewAdapter(adapter, layoutManager)
    return adapter
}
