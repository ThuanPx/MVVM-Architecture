package com.thuanpx.mvvm_architecture.data.remote.api

import com.thuanpx.mvvm_architecture.model.entity.User
import com.thuanpx.mvvm_architecture.model.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */
interface ApiService {
    @GET("/search/users")
    suspend fun searchUser(@Query("q") keyword: String, @Query("page") page: Int): BaseResponse<List<User>>
}
