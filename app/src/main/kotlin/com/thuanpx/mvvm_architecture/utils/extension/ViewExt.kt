package com.thuanpx.mvvm_architecture.utils.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/15/20.
 */

val ViewGroup.inflater: LayoutInflater get() = LayoutInflater.from(context)

inline fun View.clicks(coolDown: Long = 1000L, crossinline action: (view: View) -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        var lastTime = 0L
        override fun onClick(v: View) {
            val now = System.currentTimeMillis()
            if (now - lastTime > coolDown) {
                action(v)
                lastTime = now
            }
        }
    })
}

fun ImageView.loadImageUrl(url: String?) {
    Glide.with(context).load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
