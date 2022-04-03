package com.thuanpx.mvvm_architecture.data.remote.datasource

import androidx.lifecycle.MutableLiveData
import com.thuanpx.mvvm_architecture.base.BaseDataSource
import com.thuanpx.mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.mvvm_architecture.model.entity.Pokemon
import com.thuanpx.mvvm_architecture.model.response.BaseResponse
import com.thuanpx.mvvm_architecture.utils.coroutines.ApiResponse

/**
 * Copyright © 2022 Est Rouge VN.
 * Created by ThuanPx on 4/3/22.
 */
class PokemonDataSource(
    private val apiService: ApiService,
    isLoading: MutableLiveData<Boolean>
) : BaseDataSource<Pokemon>() {

    override val loading: MutableLiveData<Boolean> = isLoading

    override suspend fun requestMore(nextPage: Int): ApiResponse<BaseResponse<List<Pokemon>>> {
        return  apiService.fetchPokemons(page = nextPage)
    }

}