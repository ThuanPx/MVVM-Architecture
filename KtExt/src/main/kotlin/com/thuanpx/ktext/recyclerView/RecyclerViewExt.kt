package com.thuanpx.ktext.recyclerView

import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/15/20.
 */

val <VH> RecyclerView.Adapter<VH>.isEmpty: Boolean where VH : RecyclerView.ViewHolder
    get() = itemCount == 0

val <VH> RecyclerView.Adapter<VH>.isNotEmpty: Boolean where VH : RecyclerView.ViewHolder
    get() = !isEmpty

/**
 * Disable all user input to a recyclerview, passing touch events out
 */
fun RecyclerView.disableTouch() {
    this.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            return true
        }
    })
}

/**Set adapter of recyclerView
 * @param yourAdapter your adapter(must extend RecyclerView.Adapter)
 * @param layoutOrientation LinearLayoutManager orientation of adapter, default is RecyclerView.VERTICAL
 * @param fixedSize isFixed size of recyclerView, default is true*/
fun <T : RecyclerView.Adapter<*>> RecyclerView.initRecyclerViewAdapter(
    yourAdapter: T?,
    layoutOrientation: Int = RecyclerView.VERTICAL,
    fixedSize: Boolean = false,
    reverseLayout: Boolean = false
) {
    apply {
        layoutManager = LinearLayoutManager(context, layoutOrientation, reverseLayout)
        adapter = yourAdapter
        setHasFixedSize(fixedSize)
    }
}

/**Set adapter of recyclerView
 * @param yourAdapter your adapter(must extend RecyclerView.Adapter)
 * @param yourLayoutManager Pass your own layout manager
 * @param fixedSize isFixed size of recyclerView, default is true*/
fun <T : RecyclerView.Adapter<*>> RecyclerView.initRecyclerViewAdapter(
    yourAdapter: T?,
    yourLayoutManager: RecyclerView.LayoutManager,
    fixedSize: Boolean = false
) {
    apply {
        layoutManager = yourLayoutManager
        adapter = yourAdapter
        setHasFixedSize(fixedSize)
    }
}
