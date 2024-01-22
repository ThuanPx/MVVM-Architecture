package com.thuanpx.view_mvvm_architecture.utils.extension.glide

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.thuanpx.view_mvvm_architecture.utils.GlideApp

/**
 * Created by ThuanPx on 3/23/22.
 */

fun ImageView.loadImageUrl(url: String?) {
    GlideApp.with(context).load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
