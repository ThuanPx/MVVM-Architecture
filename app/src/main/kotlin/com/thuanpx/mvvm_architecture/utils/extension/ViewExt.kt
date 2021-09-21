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

fun ImageView.loadImageUrl(url: String?) {
    Glide.with(context).load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
