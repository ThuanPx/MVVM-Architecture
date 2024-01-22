package com.thuanpx.view_mvvm_architecture.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thuanpx.view_mvvm_architecture.databinding.ItemPokemonBinding
import com.thuanpx.view_mvvm_architecture.feature.home.HomeAdapter.ItemViewHolder
import com.thuanpx.view_mvvm_architecture.model.entity.Pokemon
import com.thuanpx.view_mvvm_architecture.model.entity.PokemonDiffCallback
import com.thuanpx.view_mvvm_architecture.utils.GlideApp

/**
 * Created by ThuanPx on 4/3/22.
 */
class HomeAdapter : PagingDataAdapter<Pokemon, ItemViewHolder>(PokemonDiffCallback) {

    class ItemViewHolder(
        private val viewBinding: ItemPokemonBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        fun onBindData(item: Pokemon?) {
            with(viewBinding) {
                tvName.text = item?.name
                GlideApp.with(tvImage)
                    .load(item?.getImageUrl())
                    .into(tvImage)
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
