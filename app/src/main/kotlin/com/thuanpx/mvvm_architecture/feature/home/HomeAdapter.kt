package com.thuanpx.mvvm_architecture.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.thuanpx.ktext.glide.loadImageUrl
import com.thuanpx.mvvm_architecture.databinding.ItemPokemonBinding
import com.thuanpx.mvvm_architecture.feature.home.HomeAdapter.ItemViewHolder
import com.thuanpx.mvvm_architecture.model.entity.Pokemon
import com.thuanpx.mvvm_architecture.model.entity.PokemonDiffCallback


/**
 * Copyright © 2022 Est Rouge VN.
 * Created by ThuanPx on 4/3/22.
 */
class HomeAdapter : PagingDataAdapter<Pokemon, ItemViewHolder>(PokemonDiffCallback) {

    class ItemViewHolder(
        private val viewBinding: ItemPokemonBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        fun onBindData(item: Pokemon?) {
            with(viewBinding) {
                tvName.text = item?.name
                Glide.with(tvImage)
                    .load(item?.getImageUrl())
                    .listener(
                        GlidePalette.with(item?.getImageUrl())
                            .use(BitmapPalette.Profile.MUTED_LIGHT)
                            .intoCallBack { palette ->
                                val rgb = palette?.dominantSwatch?.rgb
                                if (rgb != null) {
                                    cardView.setCardBackgroundColor(rgb)
                                }
                            }.crossfade(true)
                    ).into(tvImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBindData(getItem(position))
    }
}
