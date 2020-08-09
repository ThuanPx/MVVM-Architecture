package com.thuanpx.mvvm_architecture.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import com.thuanpx.mvvm_architecture.common.base.BaseViewModel
import com.thuanpx.mvvm_architecture.data.repository.UserRepository
import com.thuanpx.mvvm_architecture.model.entity.User
import com.thuanpx.mvvm_architecture.utils.liveData.SingleLiveData

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/8/20.
 */
class HomeViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val users = SingleLiveData<List<User>>()

    fun searchUser(keyWork: String, page: Int = 1) {
        viewModelScope(users, onRequest = {
            userRepository.searchUser(keyWork, page)
        })
    }

    fun searchUser2(keyWork: String, page: Int = 1) {
        viewModelScope(users, onRequest = {
            userRepository.searchUser(keyWork, page)
        }, onSuccess = { users.value = it })
    }

    fun searchUser3(keyWork: String, page: Int = 1) {
        viewModelScope(users,
            onRequest = {
                userRepository.searchUser(keyWork, page)
            },
            onSuccess = { users.value = it },
            onError = {
                exception.value = it
            })
    }
}
