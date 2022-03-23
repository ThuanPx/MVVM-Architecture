package com.thuanpx.mvvm_architecture.data.repository

import com.thuanpx.mvvm_architecture.data.remote.api.ApiService
import javax.inject.Inject

/**
 * Copyright © 2021 Neolab VN.
 * Created by ThuanPx on 17/09/2021.
 */

interface AppRepository

class DefaultAppRepository @Inject constructor(
    private val apiService: ApiService
) : AppRepository
