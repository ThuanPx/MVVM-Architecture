package com.thuanpx.mvvm_architecture.model.entity

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
        return "https://pokeres.bastionbot.org/images/pokemon/$index.png"
    }
}
