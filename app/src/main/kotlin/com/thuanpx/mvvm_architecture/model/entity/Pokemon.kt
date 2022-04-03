package com.thuanpx.mvvm_architecture.model.entity

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/13/20.
 */
data class Pokemon(
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null
) {
    fun getImageUrl(): String {
        val index = url?.split("/".toRegex())?.dropLast(1)?.last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$index.png"
    }
}

object PokemonDiffCallback: DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.name == newItem.name
    }
}
