package com.thuanpx.mvvm_architecture.data.remote.api

import com.thuanpx.mvvm_architecture.model.entity.Pokemon
import com.thuanpx.mvvm_architecture.model.entity.PokemonInfo
import com.thuanpx.mvvm_architecture.model.response.BaseResponse
import com.thuanpx.mvvm_architecture.utils.coroutines.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by ThuanPx on 8/5/20.
 */
interface ApiService {

    @GET("pokemon")
    suspend fun fetchPokemons(
        @Query("limit") limit: Int = 20,
        @Query("offset") page: Int = 0
    ): ApiResponse<BaseResponse<List<Pokemon>>>

    @GET("pokemon/{name}")
    suspend fun fetchPokemon(@Path("name") name: String): ApiResponse<PokemonInfo>
}
