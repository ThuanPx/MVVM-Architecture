package com.thuanpx.view_mvvm_architecture.base.network

data class NetworkError(
    val errorCode: String?,
    val message: String?,
    val status: String?
)