package com.thuanpx.mvvm_architecture.data.repository

import com.thuanpx.mvvm_architecture.common.base.BaseRepository
import com.thuanpx.mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.mvvm_architecture.model.entity.User
import com.thuanpx.mvvm_architecture.utils.DataResult
import com.thuanpx.mvvm_architecture.utils.liveData.SingleEvent
import javax.inject.Inject

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/8/20.
 */

interface UserRepository {
    suspend fun searchUser(keyword: String, page: Int = 1): DataResult<SingleEvent<List<User>>>
}

class DefaultUserRepository @Inject constructor(private val apiService: ApiService) :
    UserRepository, BaseRepository() {

    override suspend fun searchUser(
        keyword: String,
        page: Int
    ): DataResult<SingleEvent<List<User>>> {
        return withResultContext { apiService.searchUser(keyword, page).data }
    }
}
