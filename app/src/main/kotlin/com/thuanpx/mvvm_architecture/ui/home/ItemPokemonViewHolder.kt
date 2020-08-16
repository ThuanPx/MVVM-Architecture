package com.thuanpx.mvvm_architecture.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.thuanpx.mvvm_architecture.databinding.ItemHomeBinding
import com.thuanpx.mvvm_architecture.model.entity.Pokemon

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/13/20.
 */

class ItemPokemonViewHolder(private val view: ItemHomeBinding) :
    RecyclerView.ViewHolder(view.root) {
    fun onBind(item: Pokemon) {
        with(view) {
            name.text = item.name

            Glide.with(view.image.context)
                .load(item.getImageUrl())
                .listener(
                    GlidePalette.with(item.getImageUrl())
                        .use(BitmapPalette.Profile.MUTED_LIGHT)
                        .intoCallBack { palette ->
                            palette?.dominantSwatch?.rgb?.let {
                                view.root.setCardBackgroundColor(it)
                            }
                        }
                        .crossfade(true))
                .into(view.image)
        }
    }
}
