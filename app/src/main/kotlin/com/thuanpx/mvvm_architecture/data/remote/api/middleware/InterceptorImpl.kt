package com.thuanpx.mvvm_architecture.data.remote.api.middleware

import androidx.annotation.NonNull
import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class InterceptorImpl : Interceptor {

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {

        val builder = initializeHeader(chain)
        val request = builder.build()

        return chain.proceed(request)
    }

    private fun initializeHeader(chain: Interceptor.Chain): Request.Builder {
        val originRequest = chain.request()
        return originRequest.newBuilder()
            .header("Accept", "application/json")
            .addHeader("Language", "ja")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Cache-Control", "no-store")
            // TODO add token header
            // .addHeader(KEY_TOKEN, TOKEN_TYPE + accessToken)
            .method(originRequest.method, originRequest.body)
    }

    companion object {
        private const val TOKEN_TYPE = "Bearer "
        private const val KEY_TOKEN = "Authorization"
    }
}
