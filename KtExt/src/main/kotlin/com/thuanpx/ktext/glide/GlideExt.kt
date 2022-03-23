package com.thuanpx.ktext.glide

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Copyright © 2022 Est Rouge VN.
 * Created by ThuanPx on 3/23/22.
 */

fun ImageView.loadImageUrl(url: String?) {
    Glide.with(context).load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}