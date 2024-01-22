package com.thuanpx.view_mvvm_architecture.data.remote.datasource

import com.thuanpx.view_mvvm_architecture.base.BaseDataSource
import com.thuanpx.view_mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.view_mvvm_architecture.model.entity.Pokemon
import com.thuanpx.view_mvvm_architecture.model.response.BaseResponse

/**
 * Created by ThuanPx on 4/3/22.
 */
class PokemonDataSource(
    private val apiService: ApiService,
) : BaseDataSource<Pokemon>() {

    override suspend fun requestMore(nextPage: Int): BaseResponse<List<Pokemon>> {
        return apiService.fetchPokemons(page = nextPage * 20)
    }
}
