package com.thuanpx.view_mvvm_architecture.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by ThuanPx on 8/13/20.
 */
@JsonClass(generateAdapter = true)
data class PokemonInfo(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null
) {
    fun getImageUrl(): String {
        return "https://pokeres.bastionbot.org/images/pokemon/$id.png"
    }
}
