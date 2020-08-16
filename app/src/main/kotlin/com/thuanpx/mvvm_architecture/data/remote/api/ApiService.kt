package com.thuanpx.mvvm_architecture.data.remote.api

import com.thuanpx.mvvm_architecture.model.entity.Pokemon
import com.thuanpx.mvvm_architecture.model.entity.PokemonInfo
import com.thuanpx.mvvm_architecture.model.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */
interface ApiService {
    @GET("pokemon")
    suspend fun fetchPokemons(
        @Query("limit") limit: Int = 20,
        @Query("offset") page: Int = 0
    ): BaseResponse<List<Pokemon>>

    @GET("pokemon/{name}")
    suspend fun fetchPokemon(@Path("name") name: String): PokemonInfo
}
