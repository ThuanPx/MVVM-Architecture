package com.thuanpx.mvvm_architecture.model.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by ThuanPx on 8/13/20.
 */
data class PokemonInfo(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null
) {
    fun getImageUrl(): String {
        return "https://pokeres.bastionbot.org/images/pokemon/$id.png"
    }
}
